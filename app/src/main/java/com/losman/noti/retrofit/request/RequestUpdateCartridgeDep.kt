package com.losman.noti.retrofit.request

data class RequestUpdateCartridgeDep(
    var cartridges_id : List<Int>,
    var printers_id : List<Int>,
    var clear : Boolean = false
) {
}