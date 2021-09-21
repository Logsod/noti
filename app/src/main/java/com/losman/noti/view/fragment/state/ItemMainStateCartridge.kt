package com.losman.noti.view.fragment.state

import com.losman.noti.R
import com.losman.noti.databinding.RowMainStateCartridgeItemBinding
import com.losman.noti.model.CartridgeBaseStateModel
import com.xwray.groupie.databinding.BindableItem

class ItemMainStateCartridge(
    val cartridge: CartridgeBaseStateModel,
    val listener: ItemMainStateListener
) :
    BindableItem<RowMainStateCartridgeItemBinding>() {
    lateinit var binding: RowMainStateCartridgeItemBinding
    override fun bind(viewBinding: RowMainStateCartridgeItemBinding, position: Int) {
        binding = viewBinding
        binding.cartridgeAmount.text = cartridge.amount.toString()
        binding.cartridgeModel.text = cartridge.model
        binding.root.setOnClickListener {
            listener.MainStateItemClicked(cartridge, position)
        }
    }

    override fun getLayout(): Int {
        return R.layout.row_main_state_cartridge_item
    }
}