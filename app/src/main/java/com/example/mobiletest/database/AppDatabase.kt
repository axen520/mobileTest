package com.example.mobiletest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobiletest.database.dao.BookingDao
import com.example.mobiletest.entity.BookingEntity
import com.example.mobiletest.entity.BookingInfoEntity

@Database(entities = [BookingEntity::class,BookingInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}