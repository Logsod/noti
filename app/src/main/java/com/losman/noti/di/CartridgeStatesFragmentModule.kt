package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.state.MainStatePresenter
import com.losman.noti.view.fragment.state.StatesPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class CartridgeStatesFragmentModule() {
    @Provides
    @Singleton
    fun provideCartridgeStatesFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): StatesPresenter {
        return StatesPresenter(context, notiService, preferences)
    }
}