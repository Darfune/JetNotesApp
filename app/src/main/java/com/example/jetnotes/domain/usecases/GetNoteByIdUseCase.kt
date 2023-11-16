package com.example.jetnotes.domain.usecases

import com.example.jetnotes.domain.repository.Repository
import javax.inject.Inject

class GetNoteByIdUseCase  @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(id: Long) = repository.getNoteById(id)
}