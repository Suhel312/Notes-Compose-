package com.suhel.noteapp.data.mapper

import com.suhel.noteapp.data.local.Note
import com.suhel.noteapp.domain.model.NoteModel

object NoteMapper {

    fun Note.toDomain(): NoteModel = NoteModel(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        isPinned = isPinned,
        color = color
    )

    fun NoteModel.toEntity(): Note = Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        isPinned = isPinned,
        color = color
    )
}