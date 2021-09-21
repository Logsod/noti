package com.losman.noti.view.fragment.printer

import android.view.View
import com.losman.noti.R
import com.losman.noti.databinding.RowPrinterItemBinding
import com.losman.noti.model.AddPrinterRequest
import com.losman.noti.model.PrinterDevice
import com.xwray.groupie.databinding.BindableItem

class ItemPrinter(val printer: PrinterDevice, val listener: PrinterItemListener) : BindableItem<RowPrinterItemBinding>() {
    lateinit var binding: RowPrinterItemBinding
    override fun bind(viewBinding: RowPrinterItemBinding, position: Int) {
        binding = viewBinding
        binding.printerModelName.text = printer.model
        if (printer.comment.isEmpty())
            binding.printerComment.visibility = View.GONE
        else
            binding.printerComment.text = printer.comment
        binding.root.setOnClickListener { listener.PrinterItemClicked(printer,position) }
    }

    override fun getLayout(): Int {
        return R.layout.row_printer_item
    }
}