package com.suhel.noteapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isPinned: Boolean = false,
    val color: Int = 0xFFFFFFFF.toInt(),
)