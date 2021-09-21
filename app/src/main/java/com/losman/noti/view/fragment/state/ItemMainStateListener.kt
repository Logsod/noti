package com.losman.noti.view.fragment.state

import com.losman.noti.model.CartridgeBaseStateModel

interface ItemMainStateListener {
    fun MainStateItemClicked(item: CartridgeBaseStateModel, position: Int)
}