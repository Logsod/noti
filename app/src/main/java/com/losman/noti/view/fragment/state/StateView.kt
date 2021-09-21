package com.losman.noti.view.fragment.state

import com.losman.noti.model.CartridgeStateModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface StateView : MvpView {
    @Skip
    fun showToast(message : String)

    @Skip
    fun updateCartridgeList(list : List<CartridgeStateModel>)
}