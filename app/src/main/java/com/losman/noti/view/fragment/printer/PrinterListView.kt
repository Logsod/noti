package com.losman.noti.view.fragment.printer

import com.losman.noti.model.PrinterDevice
import com.losman.noti.model.PrinterModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface PrinterListView : MvpView {
    @Skip
    fun showToast(message: String)

    @Skip
    fun addPrinter(printerDevice: PrinterDevice)

    @AddToEndSingle
    fun setPrinterModelList(list: List<PrinterModel>)

    @AddToEndSingle
    fun updatePrinterList(list: List<PrinterDevice>)
}