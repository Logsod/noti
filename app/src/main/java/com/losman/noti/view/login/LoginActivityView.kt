package com.losman.noti.view.login

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface LoginActivityView : MvpView {
    @Skip
    fun showMessage(message :String)
    @Skip
    fun startMainViewActivity()
}