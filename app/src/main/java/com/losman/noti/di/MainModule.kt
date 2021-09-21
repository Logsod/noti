package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.HostSelectionInterceptor
import com.losman.noti.retrofit.NotiService
import com.losman.noti.retrofit.RetrofitApi
import com.losman.noti.view.AppConstant
import com.losman.noti.view.login.LoginActivityPresenter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class MainModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideNotiService(preferences: SharedPreferences): NotiService {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HostSelectionInterceptor(preferences))
            .cache(null)
            .build()

        return RetrofitApi(okHttpClient).getServiceApi()
    }

    @Provides
    @Singleton
    fun providePreferences(): SharedPreferences {
        return application.applicationContext.getSharedPreferences(
            "APP_PREFERENCESs",
            Context.MODE_PRIVATE
        )
    }
}
