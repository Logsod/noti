package com.losman.noti.view.fragment.printer

import com.losman.noti.model.PrinterModel

interface PrinterModelItemListener {
    fun printerModelItemClicked(model : PrinterModel, position : Int)
}