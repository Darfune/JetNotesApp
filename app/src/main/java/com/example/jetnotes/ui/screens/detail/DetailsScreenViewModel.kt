package com.example.jetnotes.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.usecases.GetNoteByIdUseCase
import com.example.jetnotes.domain.usecases.InsertNoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailsScreenViewModel @AssistedInject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    @Assisted private val noteId: Long
) : ViewModel() {
    var uiState by mutableStateOf(DetailsState())
        private set

    val isFormNotBlank: Boolean
        get() = uiState.title.isNotEmpty() && uiState.content.isNotEmpty()

    private val note: Note
        get() = uiState.run {
            Note(
                id, title, content, createdDate, isBookmarked
            )
        }

    init {
        initialize()
    }

    private fun initialize() {
        val isUpdatingNote = noteId != -1L
        uiState = uiState.copy(isUpdatingNote = isUpdatingNote)
        if (isUpdatingNote) {
            getNoteById()
        }
    }

    private fun getNoteById() = viewModelScope.launch {
        getNoteByIdUseCase(noteId).collectLatest {
            uiState = uiState.copy(
                id = note.id,
                title = note.title,
                content = note.content,
                isBookmarked = note.isBookMarked,
                createdDate = note.createdDate
            )
        }
    }

    fun onTitleChange(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun onContentChange(content: String) {
        uiState = uiState.copy(content = content)
    }

    fun onBookmarkChange(isBookmarked: Boolean) {
        uiState = uiState.copy(isBookmarked = isBookmarked)
    }

    fun addOrUpdateNote() = viewModelScope.launch {
        insertNoteUseCase(note = note)
    }

}

data class DetailsState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val isBookmarked: Boolean = false,
    val createdDate: Date = Date(),
    val isUpdatingNote: Boolean = false
)

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val noteId: Long,
    private val assistedFactory: DetailsAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(noteId) as T
    }
}

@AssistedFactory
interface DetailsAssistedFactory {
    fun create(noteId: Long): DetailsScreenViewModel
}