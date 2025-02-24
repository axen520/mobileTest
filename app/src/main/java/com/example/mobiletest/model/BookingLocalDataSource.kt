package com.example.mobiletest.model

import com.example.mobiletest.database.AppDatabaseManager
import com.example.mobiletest.database.dao.BookingDao
import com.example.mobiletest.entity.BookingEntity
import com.example.mobiletest.entity.BookingInfoEntity

/**
 * booking cache layer
 */
class BookingLocalDataSource(
    private val dao: BookingDao = AppDatabaseManager.get().db.bookingDao()
) : BookingDataSource {

    /**
     *  segments will be empty
     */
    override suspend fun getBookingInfo(): BookingInfoEntity? = dao.getBookingInfo()

    fun getBookingList(): List<BookingEntity> = dao.getBookingList()

    fun insertBookingInfo(info: BookingInfoEntity) {
        dao.insertBookingInfo(info)
        dao.insertBooking(*info.segments.toTypedArray())
    }

    fun deleteBookingInfo(info: BookingInfoEntity?) {
        info?.let { dao.deleteBookingInfo(info) }
    }

    fun deleteBookingList() {
        dao.deleteBookingList()
    }
}