package com.suhel.noteapp.domain.usecase

import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
){

    operator fun invoke(): Flow<List<NoteModel>> {
        return repository.getAllNotes()
    }
}