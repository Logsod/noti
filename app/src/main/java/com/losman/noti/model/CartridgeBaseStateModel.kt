package com.losman.noti.model

data class CartridgeBaseStateModel(
    val id: Int,
    val cartridge_id: Int,
    var amount: Int,
    val model: String
) {
}