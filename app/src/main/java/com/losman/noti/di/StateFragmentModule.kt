package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.state.StatePresenter
import com.losman.noti.view.splash.SplashActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StateFragmentModule() {

    @Provides
    fun provideStateFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): StatePresenter {
        return StatePresenter(context, notiService, preferences)
    }

}