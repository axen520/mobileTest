package com.example.mobiletest

import android.app.Application
import com.example.mobiletest.database.AppDatabaseManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
        AppDatabaseManager.init(this)
    }
}