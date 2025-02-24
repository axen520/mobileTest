package com.example.mobiletest.core

import android.content.Context
import okhttp3.Interceptor

interface IAppModule {

    fun init(application: Context)

    fun interceptors(): List<Interceptor>
}