package com.suhel.noteapp.domain.usecase

import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: NoteModel) {
        require(note.title.isNotBlank()) {"Title cant be empty"}

        repository.insertNote(note)
    }
}