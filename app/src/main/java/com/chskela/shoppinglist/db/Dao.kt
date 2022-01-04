package com.chskela.shoppinglist.db

import androidx.room.*
import androidx.room.Dao
import com.chskela.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Insert
    suspend fun insertNote(noteItem: NoteItem)

    @Update
    suspend fun updateNote(noteItem: NoteItem)

    @Delete
    suspend fun deleteNote(noteItem: NoteItem)
}