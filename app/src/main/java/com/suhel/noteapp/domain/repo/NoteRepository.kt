package com.suhel.noteapp.domain.repo

import com.suhel.noteapp.data.local.Note
import com.suhel.noteapp.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun updateNote(note: NoteModel)

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)

    fun getAllNotes(): Flow<List<NoteModel>>

    fun searchNotes(query: String): Flow<List<NoteModel>>

}