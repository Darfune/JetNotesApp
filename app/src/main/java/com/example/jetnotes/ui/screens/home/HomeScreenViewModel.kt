package com.example.jetnotes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.usecases.DeleteNoteUseCase
import com.example.jetnotes.domain.usecases.GetAllNotesUseCase
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
class HomeScreenViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getAllNotesUseCase()
            .onEach { _uiState.value = HomeUiState(notes = Resource.Success(it)) }
            .catch { _uiState.value = HomeUiState(notes = Resource.Error(it.message)) }
            .launchIn(viewModelScope)
    }

    fun deleteNote(id: Long) = viewModelScope.launch { deleteNoteUseCase(id) }

    fun updateBookmark(note: Note) =
        viewModelScope.launch {
            updateNoteUseCase(note = note.copy(isBookMarked = !note.isBookMarked))
        }
}

data class HomeUiState(
    val notes: Resource<List<Note>> = Resource.Loading,
    )