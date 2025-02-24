package com.example.mobiletest.api

import com.example.mobiletest.entity.BookingInfoEntity
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConfig.GET_BOOKING_LIST)
    suspend fun getBookingList() : BookingInfoEntity

    @GET(ApiConfig.GET_BOOKING_LIST_1)
    suspend fun getBookingList1() : BookingInfoEntity

}