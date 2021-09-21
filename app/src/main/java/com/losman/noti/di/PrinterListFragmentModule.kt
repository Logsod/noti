package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.printer.PrinterListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrinterListFragmentModule() {
    @Provides
    @Singleton
    fun providePrinterListFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): PrinterListPresenter {
        return PrinterListPresenter(context, notiService, preferences)
    }
}