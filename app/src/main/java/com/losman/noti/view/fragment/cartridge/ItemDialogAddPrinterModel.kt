package com.losman.noti.view.fragment.cartridge

import android.graphics.Color
import com.losman.noti.R
import com.losman.noti.databinding.RowSimpleTextBinding
import com.losman.noti.model.PrinterModel
import com.xwray.groupie.databinding.BindableItem

class ItemDialogAddPrinterModel(val printerModel: PrinterModel) :
    BindableItem<RowSimpleTextBinding>() {
    private var rowIsSelected: Boolean = false
    lateinit var binding: RowSimpleTextBinding
    override fun bind(viewBinding: RowSimpleTextBinding, position: Int) {
        binding = viewBinding
        binding.textView.text = printerModel.model
        binding.root.setOnClickListener { toggleSelected() }
    }

    override fun getLayout(): Int {
        return R.layout.row_simple_text
    }

    fun isSelected(): Boolean {
        return rowIsSelected
    }

    fun toggleSelected(): Boolean {
        rowIsSelected = !rowIsSelected

        if (rowIsSelected)
            binding.mainRowLayout.setBackgroundColor(Color.parseColor("#cccccc"))
        else
            binding.mainRowLayout.setBackgroundColor(Color.parseColor("#ffffff"))

        return rowIsSelected
    }

}