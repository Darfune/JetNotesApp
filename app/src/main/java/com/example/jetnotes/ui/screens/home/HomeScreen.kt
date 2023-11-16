package com.example.jetnotes.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.example.jetnotes.R
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.resourse.Resource
import com.example.jetnotes.ui.theme.JetNotesTheme
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when (state.notes) {
        is Resource.Loading -> CircularProgressIndicator()
        is Resource.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onBookmarkChange = onBookmarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
        }

        is Resource.Error -> Text(
            text = state.notes.message ?: "Unknown Error",
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun HomeDetail(
    notes: List<Note>,
    modifier: Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
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

@Composable
fun NoteCard(
    index: Int,
    note: Note,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    val isEvenIndex = index % 2 == 0
    val shapeOfCard = when {
        isEvenIndex -> RoundedCornerShape(topStart = 50f, bottomEnd = 50f)
        else -> RoundedCornerShape(topEnd = 50f, bottomStart = 50f)
    }
    val bookmarkIcon =
        if (note.isBookMarked) R.drawable.ic_not_bookmarked else R.drawable.ic_bookmarked
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        tonalElevation = 1.dp,
        shape = shapeOfCard,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = note.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDeleteNote(note.id) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_note),
                        contentDescription = "delete note",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                IconButton(onClick = { onBookmarkChange(note) }) {
                    Icon(
                        painter = painterResource(id = bookmarkIcon),
                        contentDescription = "bookmark note"
                    )
                }
            }
        }
    }
}

val placeHolderText =
    "A falsis, amor brevis compater. Magnumn."
val notes = listOf(
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "JetPack Compose",
        content = placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Android with Kotlin",
        content = placeHolderText + placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "MVVM",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "UI/UX",
        content = placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Unit Test",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Dao",
        content = placeHolderText + placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Preview",
        content = placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Text",
        content = placeHolderText + placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "JetPack Compose",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Android with Kotlin",
        content = placeHolderText + placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "MVVM",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "UI/UX",
        content = placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Unit Test",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
)

@Preview(showSystemUi = true)
@Composable
fun PrevHome() {
    JetNotesTheme {
        HomeScreen(
            state = HomeUiState(notes = Resource.Success(notes)),
            onBookmarkChange = {},
            onDeleteNote = {},
            onNoteClicked = {})
    }
}