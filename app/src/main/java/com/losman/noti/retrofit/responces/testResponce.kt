package com.losman.noti.retrofit.responces

class testResponce : ArrayList<testResponce.testResponceItem>(){
    data class testResponceItem(
        val id: Int = 0,
        val name: String = ""
    )
}