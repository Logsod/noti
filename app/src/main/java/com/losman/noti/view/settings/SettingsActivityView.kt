package com.losman.noti.view.settings

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface SettingsActivityView : MvpView {

    @Skip
    fun setupServerFields(ip: String, port: String)
}