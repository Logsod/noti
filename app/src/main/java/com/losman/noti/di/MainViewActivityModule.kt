package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.mainview.MainViewActivityPresenter
import com.losman.noti.view.splash.SplashActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainViewActivityModule() {

    @Provides
    @Singleton
    fun provideMainViewPresenter(
        context: Context,
        notiService: NotiService,
        preferences: SharedPreferences
    ): MainViewActivityPresenter {
        return MainViewActivityPresenter(context, notiService, preferences)
    }

}