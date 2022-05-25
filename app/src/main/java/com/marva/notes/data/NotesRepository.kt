package com.marva.notes.data

import androidx.lifecycle.LiveData
import com.marva.notes.data.entity.Notes
import com.marva.notes.data.room.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllData(): LiveData<List<Notes>> = notesDao.getAllData()

    //fungsi ini untuk insert/add ke dalam database
    suspend fun insertNotes(notes: Notes){
        notesDao.insertNotes(notes)
    }

    fun searchByQuery(query: String) : LiveData<List<Notes>> {
        return notesDao.searchByQuery(query)
    }

    fun shortByHighPriority(): LiveData<List<Notes>> = notesDao.sortByHighPriority()

    fun shortByLowPriority(): LiveData<List<Notes>> = notesDao.shortByLowPriority()

    suspend fun deleteAllData() = notesDao.deleteAllData()

    suspend fun deleteNote(notes: Notes) = notesDao.deleteNotes(notes)

    suspend fun updateNote(notes: Notes) = notesDao.updateNotes(notes)
}