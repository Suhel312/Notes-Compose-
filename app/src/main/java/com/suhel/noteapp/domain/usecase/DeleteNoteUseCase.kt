package com.suhel.noteapp.domain.usecase

import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: NoteModel) {
        repository.deleteNote(note)
    }
}