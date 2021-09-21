package com.losman.noti.view.fragment.printer

import com.losman.noti.R
import com.losman.noti.databinding.RowPrinterModelBinding
import com.losman.noti.model.PrinterModel
import com.xwray.groupie.databinding.BindableItem

class ItemPrinterModel(val model: PrinterModel, private val click: PrinterModelItemListener) :
    BindableItem<RowPrinterModelBinding>() {
    lateinit var binding: RowPrinterModelBinding
    override fun bind(viewBinding: RowPrinterModelBinding, position: Int) {
        binding = viewBinding
        binding.modelName.text = model.model
        binding.root.setOnClickListener {
            click.printerModelItemClicked(model, position)
        }
    }


    override fun getLayout(): Int {
        return R.layout.row_printer_model
    }
}

