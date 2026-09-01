package com.suhel.noteapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suhel.noteapp.presentation.state.NoteAction
import com.suhel.noteapp.presentation.state.NoteUiEvent
import com.suhel.noteapp.presentation.ui.components.NoteItem
import com.suhel.noteapp.presentation.viewmodel.NoteViewModel

object NoteColors {
    val colors = listOf(
        0xFFFFFFFF.toInt(), // White
        0xFFFFCDD2.toInt(), // Red
        0xFFFFE0B2.toInt(), // Orange
        0xFFFFF9C4.toInt(), // Yellow
        0xFFC8E6C9.toInt(), // Green
        0xFFBBDEFB.toInt(), // Blue
        0xFFE1BEE7.toInt()  // Purple
    )
}

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is NoteUiEvent.ShowSnackbar ->
                    snackBarHostState.showSnackbar(event.message)

                is NoteUiEvent.NoteSaved ->
                    snackBarHostState.showSnackbar("Note saved")

                is NoteUiEvent.NoteDeleted ->
                    snackBarHostState.showSnackbar("Note deleted")
            }
        }
    }

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme()
        else lightColorScheme()
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Notes",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            viewModel.onAction(NoteAction.ToggleDarkMode)
                        }
                    ) {
                        Icon(
                            imageVector = if (uiState.isDarkMode)
                                Icons.Default.LightMode
                            else
                                Icons.Default.DarkMode,
                            contentDescription = if (uiState.isDarkMode)
                                "Switch to light mode"
                            else
                                "Switch to dark mode",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = {
                        viewModel.onAction(NoteAction.SearchQueryChanged(it))
                    },
                    label = { Text(text = "Search Notes...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Color:",
                        style = MaterialTheme.typography.labelMedium,  
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    NoteColors.colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(color))
                                .border(
                                    width = if (uiState.selectedColor == color)
                                         2.dp else 0.dp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.onAction(
                                        NoteAction.ColorSelected(color)
                                    )
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.titleInput,
                    onValueChange = {
                        viewModel.onAction(NoteAction.TitleChanged(it))
                    },
                    label = { Text(text = "Title")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.contentInput,
                    onValueChange = {
                        viewModel.onAction(NoteAction.ContentChanged(it))
                    },
                    label = { Text("Description")},
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (uiState.editingNote != null) {
                        OutlinedButton(
                            onClick = {
                                viewModel.onAction(NoteAction.CancelEdit)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.onAction(NoteAction.SaveNote)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (uiState.editingNote != null )
                                "Update Note"
                            else
                                "Add Note"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading notes....",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }

                uiState.error?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                if (uiState.notes.isEmpty() && !uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (uiState.searchQuery.isEmpty())
                                    "No notes found"
                            else
                                    "No notes yet. Add your first note!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = uiState.notes,
                        key = { it.id }
                    ) { note ->
                        NoteItem(
                            note = note,
                            isDarkMode = uiState.isDarkMode,
                            onDelete = {
                                viewModel.onAction(NoteAction.DeleteNote(it))
                            },
                            onEdit = {
                                viewModel.onAction(NoteAction.EditNote(it))
                            },
                            onTogglePin = {
                                viewModel.onAction(NoteAction.TogglePin(it))
                            }
                        )
                    }
                }
            }
        }
    }
}
