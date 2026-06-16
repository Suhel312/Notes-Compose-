package com.suhel.noteapp.repo

import com.suhel.noteapp.room.Note
import com.suhel.noteapp.room.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    val getAllNotes: Flow<List<Note>> = noteDao.getAllNotes()
}