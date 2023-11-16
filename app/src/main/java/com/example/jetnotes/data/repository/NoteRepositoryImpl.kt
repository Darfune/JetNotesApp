package com.example.jetnotes.data.repository

import com.example.jetnotes.data.local.NoteDao
import com.example.jetnotes.data.local.model.Note
import com.example.jetnotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : Repository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNoteById(id: Long): Flow<Note> = noteDao.getNoteById(id)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override suspend fun delete(id: Long) = noteDao.delete(id)

    override fun getAllBookmarkedNotes(): Flow<List<Note>> = noteDao.getBookmarkedNotes()
}