package com.example.mobiletest.model

import android.net.http.NetworkException
import com.example.mobiletest.api.Api
import com.example.mobiletest.api.ApiService
import com.example.mobiletest.entity.BookingInfoEntity

/**
 * booking service layer
 */
class BookingRemoteDataSource(
    private val api: ApiService = Api.api
) : BookingDataSource {

    private var lastIndex = 1

    override suspend fun getBookingInfo(): BookingInfoEntity = if (lastIndex == 1) {
        lastIndex = 2
        api.getBookingList()
    } else {
        lastIndex = 1
        api.getBookingList1()
    }

    fun getError() {
        // mock network error
        throw Exception()
    }
}