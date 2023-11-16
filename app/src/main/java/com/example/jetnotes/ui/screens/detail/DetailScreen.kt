package com.example.jetnotes.ui.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnotes.R

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    assistedFactory: DetailsAssistedFactory,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = DetailsScreenViewModel::class.java,
        factory = DetailsViewModelFactory(
            noteId = noteId,
            assistedFactory = assistedFactory
        )
    )
    val state = viewModel.uiState
    DetailScreen(
        modifier = modifier,
        isUpdatingNote = state.isUpdatingNote,
        title = state.title,
        content = state.content,
        isBookmarked = state.isBookmarked,
        isFormNotBlank = viewModel.isFormNotBlank,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onBookmarkChange = viewModel::onBookmarkChange,
        onButtonClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigate = navigateUp
    )
}

@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookmarked: Boolean,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBookmarkChange: (Boolean) -> Unit,
    onButtonClick: () -> Unit,
    onNavigate: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TopSection(
            title = title,
            isBookmarked = isBookmarked,
            onBookmarkChange = onBookmarkChange,
            onTitleChange = onTitleChange,
            onNavigate = onNavigate
        )
        Spacer(modifier = Modifier.size(12.dp))
        AnimatedVisibility(visible = isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onButtonClick) {
                    val icon = if (isUpdatingNote) R.drawable.ic_edit else R.drawable.ic_check
                    Icon(painter = painterResource(id = icon), contentDescription = "save data")
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            label = "Content",
            onValueChange = onContentChange
        )
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookmarked: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigate: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigate) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "back arrow"
            )
        }
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            labelAlign = TextAlign.Center,
            label = "Title",
            onValueChange = onTitleChange
        )
        IconButton(onClick = { onBookmarkChange(!isBookmarked) }) {
            val bookmarkIcon =
                if (isBookmarked) R.drawable.ic_not_bookmarked else R.drawable.ic_bookmarked
            Icon(
                painter = painterResource(id = bookmarkIcon),
                contentDescription = "bookmark status"
            )
        }
    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlign: TextAlign? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}