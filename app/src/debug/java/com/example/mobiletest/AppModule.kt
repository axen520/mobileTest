package com.example.mobiletest

import android.content.Context
import com.example.mobiletest.api.ApiConfig
import com.example.mobiletest.api.ApiMock
import com.example.mobiletest.core.IAppModule
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object AppModule : IAppModule {

    private val mocks by lazy {
        hashMapOf(
            ApiConfig.GET_BOOKING_LIST to "booking.json",
            ApiConfig.GET_BOOKING_LIST_1 to "booking1.json"
        )
    }

    override fun init(application: Context) {
        ApiMock.init(application, mocks)
    }

    override fun interceptors(): List<Interceptor> = listOf(
        ApiMock.MockInterceptor(),
        HttpLoggingInterceptor()
    )
}