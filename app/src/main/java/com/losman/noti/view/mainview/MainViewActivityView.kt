package com.losman.noti.view.mainview

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface MainViewActivityView : MvpView{
    @Skip
    fun startLoginActivity()
}