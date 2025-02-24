package com.example.mobiletest.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mobiletest.entity.BookingEntity
import com.example.mobiletest.entity.BookingInfoEntity

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking_list")
    fun getBookingList(): List<BookingEntity>

    @Query("SELECT * FROM booking_info limit 1")
    fun getBookingInfo(): BookingInfoEntity?

    @Insert
    fun insertBookingInfo(info: BookingInfoEntity)

    @Delete
    fun deleteBookingInfo(info: BookingInfoEntity)

    @Query("DELETE from booking_list")
    fun deleteBookingList()

    @Insert
    fun insertBooking(vararg bookingEntity: BookingEntity)
}