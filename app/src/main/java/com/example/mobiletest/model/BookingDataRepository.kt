package com.example.mobiletest.model

import com.example.mobiletest.entity.BookingEntity
import com.example.mobiletest.entity.BookingInfoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

/**
 * booking data provider
 */
class BookingDataRepository (
    private val localDS: BookingLocalDataSource = BookingLocalDataSource(),
    private val remoteDS: BookingRemoteDataSource = BookingRemoteDataSource()
) {

    fun getBookingList(): Flow<BookingInfoEntity> = flow {
        val local = localDS.getBookingInfo()
        val curTime = System.currentTimeMillis() / 1000L
        val expireTime = local?.expiryTime?.toLong() ?: 0
        val bookingInfo = if (curTime > expireTime) {
            // this data is expired, request new data from network.
            val remote = remoteDS.getBookingInfo()
            // allow to refresh after 5 seconds
            remote.expiryTime = (curTime + 5).toString()
            // save new data into database.
            localDS.deleteBookingInfo(local)
            localDS.deleteBookingList()
            localDS.insertBookingInfo(remote)
            remote
        } else {
            // load data from database.
            local!!.copy(_segments = localDS.getBookingList())
        }
        emit(bookingInfo)
    }

    fun getError(): Flow<Unit> = flow {
        remoteDS.getError()
    }
}