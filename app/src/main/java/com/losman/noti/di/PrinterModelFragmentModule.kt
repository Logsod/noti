package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.printer.PrinterModelPresenter
import com.losman.noti.view.splash.SplashActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrinterModelFragmentModule() {
    @Provides
    @Singleton
    fun providePrinterModelFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): PrinterModelPresenter {
        return PrinterModelPresenter(context, notiService, preferences)
    }
}