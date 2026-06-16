package com.suhel.noteapp.veiwmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suhel.noteapp.repo.NoteRepository
import com.suhel.noteapp.room.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val getAllNotes: Flow<List<Note>> = repository.getAllNotes

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }
}