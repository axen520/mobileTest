package com.example.mobiletest.database

import android.content.Context
import androidx.room.Room

/**
 * database manager
 */
class AppDatabaseManager(application: Context) {

    companion object {
        private var _dbm: AppDatabaseManager? = null

        fun get(): AppDatabaseManager = _dbm!!

        fun init(application: Context): AppDatabaseManager {
            return _dbm ?: AppDatabaseManager(application).also { _dbm = it }
        }
    }

    val db by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app"
        ).build()
    }
}