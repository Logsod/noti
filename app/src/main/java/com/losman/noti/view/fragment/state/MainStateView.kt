package com.losman.noti.view.fragment.state

import com.losman.noti.model.CartridgeBaseStateModel
import com.losman.noti.model.CartridgeModel
import com.losman.noti.model.PrinterModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MainStateView : MvpView {
    @Skip
    fun showToast(message: String)

    @AddToEndSingle
    fun updatePrinterModelList(list: List<PrinterModel>)

    @AddToEndSingle
    fun updateCartridgeModelList(list: List<CartridgeModel>)

    @Skip
    fun updateCartridgeAddDialogCartridgeList(list: List<CartridgeModel>)

    @AddToEndSingle
    fun updateCartridgeList(list : List<CartridgeBaseStateModel>)

    @Skip
    fun addCartridgeToBaseStateList(cartridge: CartridgeBaseStateModel)

    @Skip
    fun showSuccessToast(message: String)
}