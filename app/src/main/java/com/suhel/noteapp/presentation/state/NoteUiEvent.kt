package com.suhel.noteapp.presentation.state

sealed class NoteUiEvent {
    data class ShowSnackbar(val message: String) : NoteUiEvent()
    data object NoteSaved : NoteUiEvent()
    data object NoteDeleted : NoteUiEvent()

}