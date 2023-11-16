package com.example.jetnotes.domain.usecases

import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterBookmarkNotesUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(): Flow<List<Note>> = repository.getAllBookmarkedNotes()
}