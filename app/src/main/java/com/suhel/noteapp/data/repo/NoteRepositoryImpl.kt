package com.suhel.noteapp.data.repo

import com.suhel.noteapp.data.local.NoteDao
import com.suhel.noteapp.data.local.Note
import com.suhel.noteapp.data.mapper.NoteMapper.toDomain
import com.suhel.noteapp.data.mapper.NoteMapper.toEntity
import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun insertNote(note: NoteModel) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: NoteModel) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: NoteModel) {
        noteDao.deleteNote(note.toEntity())
    }

    override fun getAllNotes(): Flow<List<NoteModel>> {
        return noteDao.getAllNotes()
            .map { noteEntityList ->

                noteEntityList.map { noteEntity ->
                    noteEntity.toDomain()
                }
            }
    }

    override fun searchNotes(query: String): Flow<List<NoteModel>> {
        return noteDao.searchNotes(query)
            .map { noteEntityList ->
                noteEntityList.map { noteEntity ->
                    noteEntity.toDomain()
                }
            }
    }

}