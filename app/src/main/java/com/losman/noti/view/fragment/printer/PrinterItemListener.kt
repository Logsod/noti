package com.losman.noti.view.fragment.printer

import com.losman.noti.model.PrinterDevice
import java.text.FieldPosition

interface PrinterItemListener {
    fun PrinterItemClicked(printer : PrinterDevice, position: Int)
}