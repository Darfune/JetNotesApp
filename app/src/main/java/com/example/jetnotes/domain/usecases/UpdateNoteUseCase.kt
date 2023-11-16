package com.example.jetnotes.domain.usecases

import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.repository.Repository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}