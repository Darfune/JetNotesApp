package com.example.jetnotes.ui.screens.Bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.usecases.DeleteNoteUseCase
import com.example.jetnotes.domain.usecases.FilterBookmarkNotesUseCase
import com.example.jetnotes.domain.usecases.UpdateNoteUseCase
import com.example.jetnotes.resourse.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkScreenViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val filterBookmarkNotesUseCase: FilterBookmarkNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<BookmarkUiState> = MutableStateFlow(BookmarkUiState())
    val uiState: StateFlow<BookmarkUiState> = _uiState.asStateFlow()

    init {
        getAllBookmarkedNotes()
    }

    private fun getAllBookmarkedNotes() {
        filterBookmarkNotesUseCase()
            .onEach { _uiState.value = BookmarkUiState(notes = Resource.Success(it)) }
            .catch { _uiState.value = BookmarkUiState(notes = Resource.Error(it.message)) }
            .launchIn(viewModelScope)
    }

    fun onBookmarkChange(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(note = note.copy(isBookMarked = !note.isBookMarked))
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { deleteNoteUseCase(id) }
    }
}


data class BookmarkUiState(
    val notes: Resource<List<Note>> = Resource.Loading
)