package com.losman.noti.retrofit.responces

data class SignInResponse(
    val message : String,
    val token: String,
    val success : Boolean
) {

}