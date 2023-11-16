package com.example.jetnotes.ui.screens.Bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.resourse.Resource
import com.example.jetnotes.ui.screens.home.NoteCard

@Composable
fun BookmarkScreen(
    state: BookmarkUiState,
    modifier: Modifier = Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when (state.notes) {
        is Resource.Loading -> CircularProgressIndicator()
        is Resource.Success -> {
            val notes = state.notes.data
            LazyColumn(contentPadding = PaddingValues(4.dp), modifier = modifier) {
                itemsIndexed(notes) { index, note ->
                    NoteCard(
                        index = index,
                        note = note,
                        onBookmarkChange = onBookmarkChange,
                        onDeleteNote = onDeleteNote,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }

        is Resource.Error -> Text(
            text = state.notes.message ?: "Unknown Error",
            color = MaterialTheme.colorScheme.error
        )
    }
}