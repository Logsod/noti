package com.losman.noti.view.fragment.cartridge

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.FragmentCartridgesBinding
import com.losman.noti.model.CartridgeModel
import com.losman.noti.model.PrinterModel
import com.losman.noti.view.adapter.SpinnerCustomAdapter
import com.xwray.groupie.GroupieAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CartridgesModelFragment : MvpAppCompatFragment(), CartridgesModelView,
    CartridgeModelListener {

    @Inject
    lateinit var presenterProvider: Provider<CartridgesModelPresenter>
    val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var binding: FragmentCartridgesBinding
    private val cartridgeAdapter = GroupieAdapter()
    private val selectedItems: HashMap<Int, Boolean> = hashMapOf()

    var printerModelList: List<PrinterModel> = listOf()
    var selectedRowCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //(activity as MainViewActivity?)?.setActionBarTitle("title")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        presenter.getAllCartridgeModel()
        presenter.getAllPrinterModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cartridges, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddCartridgeModel.setOnClickListener {
            showAddCartridgeModelDialog()
        }
        binding.recyclerViewCartridgeList.adapter = cartridgeAdapter
        binding.recyclerViewCartridgeList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        val array: List<String> = listOf(
            "Добавить принтер",
            "Удалить принтеры",
            "Удалить модель",
            "Действие",
        )

        val adapter =
            SpinnerCustomAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                array
            )
        binding.spinnerActionList.adapter = adapter
        binding.spinnerActionList.setSelection(adapter.count)


        binding.spinnerActionList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 0) {
                        showAddPrinterModelToCartridgeDialog()
                    }
                    if (p2 == 1) {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Отчистить?")
                            setNegativeButton("No") { alertDialog, which ->
                                alertDialog.cancel()
                                alertDialog.dismiss()
                            }
                            setPositiveButton("Yes") { alertDialog, which ->
                                presenter.clearCartridgeModelDep(getSelectedCartridgeItems())
                            }
                            create()
                            show()
                        }
                    }
                    if (p2 == 2) {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Удалить?")
                            setNegativeButton("No") { alertDialog, which ->
                                alertDialog.cancel()
                                alertDialog.dismiss()
                            }
                            setPositiveButton("Yes") { alertDialog, which ->
                                presenter.deleteCartridgeModel(getSelectedCartridgeItems())
                            }
                            create()
                            show()
                        }

                    }

                    binding.spinnerActionList.setSelection(adapter.count)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
    }

    fun getSelectedCartridgeItems(): List<Int> {
        val cartridgesList = mutableListOf<Int>()
        for (i in 0 until cartridgeAdapter.itemCount) {
            val item = cartridgeAdapter.getItem(i) as ItemCartridgeModel
            if (item.isSelected())
                cartridgesList.add(item.cartridge.id)
        }
        return cartridgesList
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun addCartridgeModel(cartridge: CartridgeModel) {
        cartridgeAdapter.add(ItemCartridgeModel(cartridge, this))
    }

    override fun updateCartridgeModelList(list: List<CartridgeModel>) {
        cartridgeAdapter.clear()
        list.forEach {
            val item = ItemCartridgeModel(it, this)
            if (selectedItems.containsKey(it.id))
                item.setSelected(true)
            cartridgeAdapter.add(item)
        }
    }

    override fun updatePrinterModelList(list: List<PrinterModel>) {
        printerModelList = list
    }

    override fun removeCartridgeModelsFromList(id: List<Int>) {
        val mList = id.toMutableList()
        val itemListToRemove: MutableList<ItemCartridgeModel> = mutableListOf()
        for (i in 0 until cartridgeAdapter.itemCount) {
            val item = cartridgeAdapter.getItem(i) as ItemCartridgeModel
            if (mList.contains(item.cartridge.id)) {

                itemListToRemove.add(item)
                mList.remove(item.cartridge.id)
            }
        }
        cartridgeAdapter.removeAll(itemListToRemove)
        cartridgeAdapter.notifyDataSetChanged()
        updateSelectedRowCount()
    }

    fun showAddPrinterModelToCartridgeDialog() {
        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_add_printer_model_to_cartridge)

        val printerModelListAdapter = GroupieAdapter()
        val recyclerView =
            dialog.findViewById<RecyclerView>(R.id.recycler_view_dialog_add_printer_to_cartridge_model_list)
        val applyButton =
            dialog.findViewById<Button>(R.id.button_dialog_add_printer_to_cartridge_add_dep)
        val cancelButton =
            dialog.findViewById<Button>(R.id.button_cancel)

        recyclerView.adapter = printerModelListAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        dialog.show()
        printerModelList.forEach {
            printerModelListAdapter.add(ItemDialogAddPrinterModel(it))
        }

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        applyButton.setOnClickListener {
            val cartridgesList = mutableListOf<Int>()
            val printersList = mutableListOf<Int>()

            for (i in 0 until printerModelListAdapter.itemCount) {
                val item = printerModelListAdapter.getItem(i) as ItemDialogAddPrinterModel
                if (item.isSelected()) {
                    printersList.add(item.printerModel.id)
                }
            }

            for (i in 0 until cartridgeAdapter.itemCount) {
                val item = cartridgeAdapter.getItem(i) as ItemCartridgeModel
                if (item.isSelected())
                    cartridgesList.add(item.cartridge.id)
            }
            presenter.updateCartridgeModelDep(cartridgesList, printersList)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showAddCartridgeModelDialog() {

        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_add_cartdrige_model)

        val addModelButton = dialog.findViewById<Button>(R.id.button_dialog_add_cartridge_model)
        val modelName =
            dialog.findViewById<TextView>(R.id.text_view_dialog_add_cartridge_model_model_name)

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        addModelButton.setOnClickListener {
            presenter.addCartridgeModel(modelName.text.toString().trim())
            dialog.dismiss()
        }
    }

    override fun onItemCartridgeModelClick(cartridge: CartridgeModel, position: Int) {
        val item = cartridgeAdapter.getItem(position) as ItemCartridgeModel
        item.toggleSelected()
        updateSelectedRowCount()
        if (item.isSelected())
            selectedItems[item.cartridge.id] = item.isSelected()
        else
            if (selectedItems.containsKey(item.cartridge.id))
                selectedItems.remove(item.cartridge.id)
    }

    fun updateSelectedRowCount() {
        var count = 0
        for (i in 0 until cartridgeAdapter.itemCount) {
            val item = cartridgeAdapter.getItem(i) as ItemCartridgeModel
            if (item.isSelected())
                count++

        }
        selectedRowCount = count

        if (selectedRowCount > 0) {
            binding.buttonActionCartridgeModel.visibility = View.VISIBLE
            binding.spinnerActionList.visibility = View.VISIBLE
            binding.buttonAddCartridgeModel.visibility = View.GONE
        } else {
            binding.buttonActionCartridgeModel.visibility = View.GONE
            binding.spinnerActionList.visibility = View.GONE
            binding.buttonAddCartridgeModel.visibility = View.VISIBLE
        }

        binding.buttonActionCartridgeModel.text = selectedRowCount.toString()
    }

}