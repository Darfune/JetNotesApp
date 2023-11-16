package com.example.jetnotes.domain.repository

import com.example.jetnotes.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllNotes(): Flow<List<Note>>

    fun getNoteById(id: Long): Flow<Note>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun delete(id: Long)

    fun getAllBookmarkedNotes(): Flow<List<Note>>
}