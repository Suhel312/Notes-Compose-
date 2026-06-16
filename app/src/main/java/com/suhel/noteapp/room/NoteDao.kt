package com.suhel.noteapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("""SELECT * FROM notes
        WHERE title LIKE '%' || :query || '%'
        OR content LIKE '%' || :query || '%'
        ORDER BY isPinned DESC, timestamp DESC """)
    fun searchNotes(query: String): Flow<List<Note>>
}