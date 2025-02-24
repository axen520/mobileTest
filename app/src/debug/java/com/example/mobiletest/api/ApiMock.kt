package com.example.mobiletest.api

import android.content.Context
import androidx.collection.LruCache
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.ref.SoftReference

/**
 * api mock tool
 */
class ApiMock private constructor(
    private val application: Context,
    private val mocks: Map<String, String>
) {

    companion object {

        private var _mock: ApiMock? = null

        val mock: ApiMock? get() = _mock

        fun init(application: Context, mocks: Map<String, String>): ApiMock {
            return _mock ?: ApiMock(application, mocks).also { _mock = it }
        }
    }

    private val mockLruCaches by lazy {
        LruCache<String, SoftReference<String>>(16)
    }

    private fun mock(request: Request): Response? {
        val path = request.url().url().path.substring(1)
        val file = mocks[path] ?: return null
        val responseText = mockLruCaches[file]?.get() ?: let {
            val ins = application.assets.open(file)
            val json = BufferedReader(InputStreamReader(ins)).use {
                it.readText()
            }
            mockLruCaches.put(file, SoftReference(json))
            json
        }
        val responseBody = ResponseBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            responseText
        )
        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .message("OK")
            .code(200)
            .body(responseBody)
            .build()
    }

    /**
     * okhttp response mock interceptor
     */
    class MockInterceptor : okhttp3.Interceptor {
        override fun intercept(chain: okhttp3.Interceptor.Chain): Response = chain.request()
            .let { mock?.mock(it) ?: chain.proceed(it) }
    }
}