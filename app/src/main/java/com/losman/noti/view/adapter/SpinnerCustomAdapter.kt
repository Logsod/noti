package com.losman.noti.view.adapter

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerCustomAdapter(context: Context?, resource: Int, private val dates: List<String>) :
    ArrayAdapter<String?>(context!!, resource, dates) {

    override fun getCount(): Int {
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}