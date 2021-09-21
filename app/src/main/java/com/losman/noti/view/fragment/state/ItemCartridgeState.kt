package com.losman.noti.view.fragment.state

import com.losman.noti.R
import com.losman.noti.databinding.RowCartridgeStateBinding
import com.losman.noti.model.CartridgeStateModel
import com.xwray.groupie.databinding.BindableItem

class ItemCartridgeState(
    val cartridgeStateModel: CartridgeStateModel,
    val listener: ItemCartridgeStateListener
) :
    BindableItem<RowCartridgeStateBinding>() {
    lateinit var binding: RowCartridgeStateBinding
    override fun bind(viewBinding: RowCartridgeStateBinding, position: Int) {
        binding = viewBinding
        binding.cartridgeModel.text = cartridgeStateModel.model
        binding.root.setOnClickListener {
            listener.stateItemClicked(cartridgeStateModel, position)
        }
    }

    override fun getLayout(): Int {
        return R.layout.row_cartridge_state
    }
}