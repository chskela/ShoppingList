package com.chskela.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chskela.shoppinglist.entities.LibraryItem
import com.chskela.shoppinglist.entities.NoteItem
import com.chskela.shoppinglist.entities.ShoppingListItem
import com.chskela.shoppinglist.entities.ShoppingListNames

@Database(entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListNames::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun getDatabase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "shopping_list.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}