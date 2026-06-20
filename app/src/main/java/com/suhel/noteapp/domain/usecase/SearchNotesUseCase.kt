package com.suhel.noteapp.domain.usecase

import androidx.room.Query
import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    operator fun invoke(query: String): Flow<List<NoteModel>> {
        return repository.searchNotes(query)
    }
}