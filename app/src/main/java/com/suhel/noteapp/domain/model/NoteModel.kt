package com.suhel.noteapp.domain.model

data class NoteModel(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isPinned: Boolean = false,
    val color: Int = 0xFFFFFFFF.toInt()
)
