package com.suhel.noteapp.presentation.ui.components

import android.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.suhel.noteapp.domain.model.NoteModel
import com.suhel.noteapp.presentation.state.NoteAction
import kotlinx.coroutines.joinAll

@Composable
fun NoteItem(
    note: NoteModel,
    isDarkMode: Boolean,
    onDelete: (NoteModel) -> Unit,
    onEdit: (NoteModel) -> Unit,
    onTogglePin: (NoteModel) -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {swipeDirection ->
            when (swipeDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete(note)
                    true
                }

                SwipeToDismissBoxValue.StartToEnd -> false

                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    val targetColor = if (isDarkMode) {
        Color(note.color).copy(alpha = 0.15f)
    } else {
        Color(note.color)
    }
    val cardColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 300),
        label = "cardColorAnimation"
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFB00020))
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.White
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable{onEdit(note)},

            colors = CardDefaults.cardColors(
                containerColor = cardColor,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),

            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = { onTogglePin(note) }
                    ) {
                        Icon(
                            imageVector = if (note.isPinned)
                                Icons.Default.PushPin
                            else
                                Icons.Outlined.PushPin,

                            contentDescription = if (note.isPinned)
                            "Unpin Note"
                            else
                            "Pin Note",

                            tint = when {
                                note.isPinned && isDarkMode ->
                                    Color(0xFF82B1FF)
                                note.isPinned && !isDarkMode ->
                                    Color(0xFF1565C0)
                                else ->
                                    MaterialTheme.colorScheme.onSurface
                                        .copy(alpha = 0.4f)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = formatTimestamp(note.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long) : String {
    val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}