package com.chskela.shoppinglist.viewmodels

import androidx.lifecycle.*
import com.chskela.shoppinglist.db.MainDatabase
import com.chskela.shoppinglist.entities.NoteItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDatabase) : ViewModel() {
    private val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    fun insertNote(noteItem: NoteItem) {
        viewModelScope.launch {
            dao.insertNote(noteItem)
        }
    }

    class MainViewModelFactory(val database: MainDatabase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }

    }
}