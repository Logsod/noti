package com.losman.noti.view.fragment.cartridge

import com.losman.noti.model.CartridgeModel
import com.losman.noti.model.PrinterModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface CartridgesModelView : MvpView {
    @Skip
    fun showToast(message: String)

    @Skip
    fun addCartridgeModel(cartridge: CartridgeModel)

    @AddToEndSingle
    fun updateCartridgeModelList(list: List<CartridgeModel>)

    @AddToEndSingle
    fun updatePrinterModelList(list: List<PrinterModel>)

    @Skip
    fun removeCartridgeModelsFromList(id: List<Int>)

}