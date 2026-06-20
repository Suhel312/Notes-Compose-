package com.suhel.noteapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.suhel.noteapp.data.local.Note

@Composable
fun NoteItem(note: Note){
    Card(modifier = Modifier.fillMaxWidth()
        .padding(8.dp)
        .wrapContentHeight()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = Int.MAX_VALUE
            )

        }
    }
}