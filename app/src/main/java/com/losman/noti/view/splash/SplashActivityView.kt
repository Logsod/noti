package com.losman.noti.view.splash

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface SplashActivityView : MvpView{
    @Skip
    fun startLoginActivity()
    @Skip
    fun startMainViewActivity()
    @Skip
    fun showErrorToast(message : String)
    @Skip
    fun showRetryButton()
    @Skip
    fun connectionError(errorMessage: String)
}