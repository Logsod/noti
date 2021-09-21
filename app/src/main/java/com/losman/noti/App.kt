package com.losman.noti

import android.app.Application
import com.losman.noti.di.*

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        appComponent =
            DaggerAppComponent.builder()
                .mainModule(MainModule(this))
                .build()
        super.onCreate()
    }
}