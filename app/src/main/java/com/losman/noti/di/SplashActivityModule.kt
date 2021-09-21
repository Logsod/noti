package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.retrofit.RetrofitApi
import com.losman.noti.view.login.LoginActivityPresenter
import com.losman.noti.view.splash.SplashActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SplashActivityModule() {

    @Provides
    @Singleton
    fun provideSplashPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): SplashActivityPresenter {
        return SplashActivityPresenter(context, notiService, preferences)
    }

}
