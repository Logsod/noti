package com.losman.noti.retrofit.request

data class RequestDeleteCartridgeModels(
    val printer_id: List<Int>,
    val delete: Boolean = true
)
