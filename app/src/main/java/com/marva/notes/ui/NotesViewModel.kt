package com.marva.notes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.marva.notes.data.NotesRepository
import com.marva.notes.data.entity.Notes
import com.marva.notes.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: NotesRepository = NotesRepository(notesDao)

    fun getAllData(): LiveData<List<Notes>> = repository.getAllData()

    //fungsi ini yang akan digunakan oleh view ketika input data dan dikirimkan ke dalam repository
    fun insertData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertNotes(notes)
        }
    }

    fun searchByQuery(query: String) : LiveData<List<Notes>> {
        return repository.searchByQuery(query)
    }

    fun shortByHighPriority(): LiveData<List<Notes>> = repository.shortByHighPriority()

    fun shortByLowPriority(): LiveData<List<Notes>> = repository.shortByLowPriority()

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(notes)
        }
    }

    fun updateNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(notes)
        }
    }
}