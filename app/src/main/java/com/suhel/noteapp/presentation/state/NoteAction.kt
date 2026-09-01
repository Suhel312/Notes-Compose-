package com.suhel.noteapp.presentation.state

import com.suhel.noteapp.data.local.Note
import com.suhel.noteapp.domain.model.NoteModel

sealed class NoteAction {
    data class TitleChanged(val title: String) : NoteAction()
    data class ContentChanged(val content: String) : NoteAction()
    data class ColorSelected(val color: Int) : NoteAction()
    data class SearchQueryChanged(val query: String) : NoteAction()
    data class EditNote(val note: NoteModel) : NoteAction()
    data class DeleteNote(val note: NoteModel) : NoteAction()
    data class TogglePin(val note: NoteModel) : NoteAction()
    data object SaveNote : NoteAction()
    data object CancelEdit : NoteAction()
    data object ToggleDarkMode : NoteAction()

}