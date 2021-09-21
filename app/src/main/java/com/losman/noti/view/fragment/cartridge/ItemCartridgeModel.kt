package com.losman.noti.view.fragment.cartridge

import android.view.View
import com.losman.noti.R
import com.losman.noti.databinding.RowCartridgeModelBinding
import com.losman.noti.model.CartridgeModel
import com.xwray.groupie.databinding.BindableItem

class ItemCartridgeModel(val cartridge: CartridgeModel, val listener: CartridgeModelListener) :
    BindableItem<RowCartridgeModelBinding>() {
    lateinit var binding: RowCartridgeModelBinding
    var rowIsSelected: Boolean = false
    override fun bind(viewBinding: RowCartridgeModelBinding, position: Int) {
        binding = viewBinding
        binding.textViewCartridgeModel.text = cartridge.model

        if(cartridge.depString != null) {
            binding.textViewCartridgeDep.text = cartridge.depString.trim()

            if (cartridge.depString.isEmpty() || cartridge.depString == "") {
                binding.textViewCartridgeDep.visibility = View.GONE;
            } else {
                binding.textViewCartridgeDep.visibility = View.VISIBLE;
            }
        }
        else
            binding.textViewCartridgeDep.visibility = View.GONE;

        binding.selected = rowIsSelected
        binding.root.setOnClickListener {
            listener.onItemCartridgeModelClick(cartridge, position)
        }

    }


    fun setSelected(s: Boolean) {
        rowIsSelected = s
    }

    fun isSelected(): Boolean {
        return rowIsSelected
    }

    fun toggleSelected(): Boolean {
        rowIsSelected = !rowIsSelected
        updateBackgroundSelectedColor()
        return rowIsSelected
    }

    fun updateBackgroundSelectedColor() {
//        if (rowIsSelected)
//            binding.mainRowLayout.setBackgroundColor(Color.parseColor("#cccccc"))
//        else
//            binding.mainRowLayout.setBackgroundColor(Color.parseColor("#ffffff"))

        binding.selected = rowIsSelected
    }

    override fun getLayout(): Int {
        return R.layout.row_cartridge_model
    }
}