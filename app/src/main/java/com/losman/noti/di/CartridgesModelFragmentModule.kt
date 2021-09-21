package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.fragment.cartridge.CartridgesModelPresenter
import com.losman.noti.view.fragment.printer.PrinterListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CartridgesModelFragmentModule() {
    @Provides
    @Singleton
    fun provideCartridgesModelFragmentPresenter(context: Context, notiService: NotiService, preferences: SharedPreferences): CartridgesModelPresenter {
        return CartridgesModelPresenter(context, notiService, preferences)
    }
}