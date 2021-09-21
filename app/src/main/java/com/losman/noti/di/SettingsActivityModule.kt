package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.settings.SettingsActivityPresenter
import com.losman.noti.view.splash.SplashActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SettingsActivityModule() {

    @Provides
    @Singleton
    fun provideSettingsPresenter(
        context: Context,
        notiService: NotiService,
        preferences: SharedPreferences,
    ): SettingsActivityPresenter {
        return SettingsActivityPresenter(context, notiService, preferences)
    }

}
