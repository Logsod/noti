package com.losman.noti.view.fragment.cartridge

import com.losman.noti.model.CartridgeModel
import java.text.FieldPosition

interface CartridgeModelListener {
    fun onItemCartridgeModelClick(cartridge : CartridgeModel, position: Int)
}