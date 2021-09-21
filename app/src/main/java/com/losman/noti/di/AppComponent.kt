package com.losman.noti.di

import com.losman.noti.StatesFragment
import com.losman.noti.view.fragment.cartridge.CartridgesModelFragment
import com.losman.noti.view.fragment.printer.PrinterListFragment
import com.losman.noti.view.fragment.printer.PrinterModelFragment
import com.losman.noti.view.fragment.state.MainStateFragment
import com.losman.noti.view.fragment.state.StateFragment
import com.losman.noti.view.login.LoginActivity
import com.losman.noti.view.mainview.MainViewActivity
import com.losman.noti.view.settings.SettingsActivity
import com.losman.noti.view.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LoginActivityModule::class,
        SplashActivityModule::class,
        MainViewActivityModule::class,
        PrinterModelFragmentModule::class,
        PrinterListFragmentModule::class,
        CartridgesModelFragmentModule::class,
        MainStateFragmentModule::class,
        CartridgeStatesFragmentModule::class,
        StateFragmentModule::class,
        SettingsActivityModule::class,
        MainModule::class
    ]
)
interface AppComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: MainViewActivity)
    fun inject(activity: SettingsActivity)
    fun inject(fragment: PrinterModelFragment)
    fun inject(fragment: PrinterListFragment)
    fun inject(fragment: CartridgesModelFragment)
    fun inject(fragment: MainStateFragment)
    fun inject(fragment: StatesFragment)
    fun inject(fragment: StateFragment)
}