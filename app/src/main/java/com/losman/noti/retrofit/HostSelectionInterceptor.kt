package com.losman.noti.retrofit

import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.view.AppConstant
import okhttp3.Interceptor
import okhttp3.Response

class HostSelectionInterceptor(val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        val host = preferences.getString(AppConstant.SETTINGS_SERVER_IP, "127.0.0.1")
        val port = preferences.getString(AppConstant.SETTINGS_SERVER_PORT, "8000")

        val newUrl = request.url().newBuilder()
            .host(host!!)
            .port(Integer.parseInt(port!!))
            .build()

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }

}