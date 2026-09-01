package com.suhel.noteapp.presentation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.usecase.AddNoteUseCase
import com.suhel.noteapp.domain.usecase.DeleteNoteUseCase
import com.suhel.noteapp.domain.usecase.GetAllNotesUseCase
import com.suhel.noteapp.domain.usecase.SearchNotesUseCase
import com.suhel.noteapp.domain.usecase.TogglePinUseCase
import com.suhel.noteapp.domain.usecase.UpdateNoteUseCase
import com.suhel.noteapp.presentation.state.NoteAction
import com.suhel.noteapp.presentation.state.NoteUiEvent
import com.suhel.noteapp.presentation.state.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val searchNoteUseCase: SearchNotesUseCase,
    private val togglePinUseCase: TogglePinUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<NoteUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            _uiState
                .map { it.searchQuery }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isEmpty()) {
                        getAllNotesUseCase()
                    } else {
                        searchNoteUseCase(query)
                    }
                }
                .catch { exception ->
                    _uiState.update { it.copy(error = exception.message) }
                }
                .collect { noteList ->
                    _uiState.update {
                        it.copy(
                            notes = noteList,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: NoteAction) {
        when (action) {
            is NoteAction.TitleChanged ->
                _uiState.update { it.copy(titleInput = action.title) }

            is NoteAction.ContentChanged ->
                _uiState.update { it.copy(contentInput = action.content) }

            is NoteAction.ColorSelected ->
                _uiState.update { it.copy(selectedColor = action.color) }

            is NoteAction.SearchQueryChanged ->
                _uiState.update { it.copy(searchQuery = action.query) }

            is NoteAction.EditNote ->
                _uiState.update {
                    it.copy(
                        editingNote = action.note,
                        titleInput = action.note.title,
                        contentInput = action.note.content,
                        selectedColor = action.note.color
                    )
                }

            is NoteAction.CancelEdit ->
                _uiState.update {
                    it.copy(
                        editingNote = null,
                        titleInput = "",
                        contentInput = "",
                        selectedColor = 0xFFFFFFFF.toInt()
                    )
                }

            is NoteAction.ToggleDarkMode ->
                _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }

            is NoteAction.SaveNote -> saveNote()
            is NoteAction.DeleteNote -> deleteNote(action.note)
            is NoteAction.TogglePin -> togglePin(action.note)
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val state = _uiState.value

            try {
                if (state.editingNote != null) {
                    updateNoteUseCase(
                        state.editingNote.copy(
                            title = state.titleInput,
                            content = state.contentInput,
                            color = state.selectedColor
                        )
                    )
                    _uiEvent.send(NoteUiEvent.ShowSnackbar("Note updated!"))
                } else {
                    addNoteUseCase(
                        NoteModel(
                            id = 0,
                            title = state.titleInput,
                            content = state.contentInput,
                            timestamp = System.currentTimeMillis(),
                            color = state.selectedColor
                        )
                    )
                    _uiEvent.send(NoteUiEvent.NoteSaved)
                }

                _uiState.update {
                    it.copy(
                        editingNote = null,
                        titleInput = "",
                        contentInput = "",
                        selectedColor = 0xFFFFFFFf.toInt()
                    )
                }
            } catch (e: IllegalArgumentException) {
                _uiEvent.send(
                    NoteUiEvent.ShowSnackbar(e.message ?: "Something went wrong")
                )
            }
        }
    }

    private fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
            _uiEvent.send(NoteUiEvent.NoteDeleted)
        }
    }

    private fun togglePin(note: NoteModel) {
        viewModelScope.launch {
            togglePinUseCase(note)
        }
    }
}