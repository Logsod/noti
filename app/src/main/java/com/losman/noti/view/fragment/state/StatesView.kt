package com.losman.noti.view.fragment.state

import com.losman.noti.model.CartridgeLabelStateModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface StatesView : MvpView {
    @Skip
    fun showToast(message : String)
    @Skip
    fun updateStateTab(list : List<CartridgeLabelStateModel>)
}