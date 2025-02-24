package com.example.mobiletest.model

import com.example.mobiletest.entity.BookingInfoEntity

/**
 * booking data layer
 */
interface BookingDataSource {

    suspend fun getBookingInfo(): BookingInfoEntity?
}