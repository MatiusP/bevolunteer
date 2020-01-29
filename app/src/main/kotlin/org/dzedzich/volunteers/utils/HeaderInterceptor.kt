package org.dzedzich.volunteers.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by aleksejskrobot on 15.07.16.
 */

class HeaderInterceptor(private var token: String?) : Interceptor {

    private val TAG = HeaderInterceptor::class.java.toString()

    fun setToken(token: String?) {
        this.token = token
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request

        Log.d(TAG, "token: " + token)
        Log.d(TAG, Constants.HEADERS.REQUEST_FROM_MOBILE_PLATFORM + ": 1")

        newRequest = request.newBuilder()
                .addHeader(Constants.HEADERS.TOKEN, token ?: "")
                .addHeader(Constants.HEADERS.REQUEST_FROM_MOBILE_PLATFORM, "1")
                .build()

        return chain.proceed(newRequest)
    }

}
