package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.printer.PrinterListPresenter
import com.losman.noti.view.fragment.state.MainStatePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainStateFragmentModule() {
    @Provides
    @Singleton
    fun provideMainStateFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): MainStatePresenter {
        return MainStatePresenter(context, notiService, preferences)
    }
}