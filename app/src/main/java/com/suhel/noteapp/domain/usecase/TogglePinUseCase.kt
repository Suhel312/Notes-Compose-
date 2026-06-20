package com.suhel.noteapp.domain.usecase

import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import javax.inject.Inject

class TogglePinUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: NoteModel) {
        val updateNote = note.copy(isPinned = !note.isPinned)
        repository.updateNote(updateNote)
    }
}