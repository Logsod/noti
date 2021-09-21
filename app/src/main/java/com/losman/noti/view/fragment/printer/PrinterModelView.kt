package com.losman.noti.view.fragment.printer

import com.losman.noti.model.PrinterModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface PrinterModelView : MvpView {
    @Skip
    fun showMessage()

    @Skip
    fun  showToast(message : String)

    @AddToEndSingle
    fun updatePrinterModelList(models : List<PrinterModel>)
}