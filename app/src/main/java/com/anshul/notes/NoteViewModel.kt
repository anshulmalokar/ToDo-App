package com.anshul.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository:NoteRepository
    val allNotes:LiveData<List<Note>>
    init {
        val dao=NoteDataBase.getDatabase(application).getNoteDao()
        repository=NoteRepository(dao)
        allNotes=repository.allNotes
    }
    fun deleteNode(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun insert(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
}