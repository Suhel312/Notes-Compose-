package com.suhel.noteapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suhel.noteapp.data.local.Note
import com.suhel.noteapp.veiwmodel.NoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NoteScreen(viewModel: NoteViewModel = hiltViewModel()) {

    val notes by viewModel.getAllNotes.collectAsState(initial = emptyList())

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(
            value = title,
            onValueChange = {title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = content,
            onValueChange = {content = it},
            label = { Text("Note Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.insertNote(
                Note(0, title, content, System.currentTimeMillis())
            )
                title = ""
                content = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn() {
            items(notes){ note ->
                NoteItem(note)
            }
        }
    }
}
