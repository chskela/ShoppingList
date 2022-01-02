package com.chskela.shoppinglist.activities

import android.app.Application
import com.chskela.shoppinglist.db.MainDatabase

class MainApp : Application() {
    val database by lazy { MainDatabase.getDatabase(this) }
}