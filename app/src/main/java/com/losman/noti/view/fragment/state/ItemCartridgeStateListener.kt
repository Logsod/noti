package com.losman.noti.view.fragment.state

import com.losman.noti.model.CartridgeStateModel
import java.text.FieldPosition

interface ItemCartridgeStateListener {
    fun stateItemClicked(item : CartridgeStateModel, position: Int)
}