package com.example.mobiletest

import android.content.Context
import com.example.mobiletest.core.IAppModule
import okhttp3.Interceptor

object AppModule : IAppModule {

    override fun init(application: Context) = Unit

    override fun interceptors(): List<Interceptor> = emptyList()
}