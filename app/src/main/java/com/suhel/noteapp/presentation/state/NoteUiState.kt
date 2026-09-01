package com.suhel.noteapp.presentation.state

import com.suhel.noteapp.domain.model.NoteModel

data class NoteUiState(
    val notes: List<NoteModel> = emptyList(),
    val searchQuery: String = "",
    val selectedColor: Int = 0xFFFFFFFF.toInt(),
    val editingNote: NoteModel? = null,
    val titleInput: String = "",
    val contentInput: String = "",
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null)
