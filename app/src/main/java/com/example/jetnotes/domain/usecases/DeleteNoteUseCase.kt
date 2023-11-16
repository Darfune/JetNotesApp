package com.example.jetnotes.domain.usecases

import com.example.jetnotes.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(id: Long) = repository.delete(id)
}