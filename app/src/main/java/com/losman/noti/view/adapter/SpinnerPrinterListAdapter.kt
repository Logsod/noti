package com.losman.noti.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class SpinnerPrinterListAdapter(context: Context?, resource: Int, private val dates: List<String>) :
    ArrayAdapter<String?>(context!!, resource, dates) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }
}