package com.losman.noti.view.fragment.state

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.textfield.TextInputEditText
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.FragmentMainStateBinding
import com.losman.noti.model.CartridgeBaseStateModel
import com.losman.noti.model.CartridgeModel
import com.losman.noti.model.PrinterModel
import com.losman.noti.view.adapter.SpinnerCustomAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import com.xwray.groupie.GroupieAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainStateFragment : MvpAppCompatFragment(), MainStateView, ItemMainStateListener {

    @Inject
    lateinit var presenterProvider: Provider<MainStatePresenter>
    val presenter by moxyPresenter { presenterProvider.get() }
    lateinit var binding: FragmentMainStateBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var dialogAddCartridgeCartridgeList: List<CartridgeModel> = listOf()
    var dialogAddCartridge: Dialog? = null
    var dialogAddDartridgeSelectedCartridgeModel: CartridgeModel? = null

    var printerModelList: List<PrinterModel> = listOf()
    var cartridgeModelList: List<CartridgeModel> = listOf()
    var printerModelStringArray: MutableList<String> = mutableListOf()

    val cartridgeListAdapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_state, container, false)
        binding.buttonAddCartridge.setOnClickListener { showAddCartridgeModelDialog() }
        binding.recyclerViewCartridgeList.adapter = cartridgeListAdapter
        binding.recyclerViewCartridgeList.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        )
        updateLists()
        return binding.root
    }


    fun updateLists() {
        presenter.getAllCartridgeModel()
        presenter.getAllPrinterModel()
        presenter.getAllBaseStateCartridges()
    }

    private fun showAddCartridgeModelDialog() {

        dialogAddDartridgeSelectedCartridgeModel = null
        dialogAddCartridge = Dialog(requireView().context)
        dialogAddCartridge?.let { dialog ->
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_add_cartridge_to_base_state)

            val spinnerPrinterModelFilter =
                dialog.findViewById<Spinner>(R.id.spinner_printer_model_filter)
            val textEditAmount = dialog.findViewById<TextInputEditText>(R.id.text_edit_amount)

            val buttonAddCartridge = dialog.findViewById<Button>(R.id.button_add_cartridge)

            val printerFilterArrayAdapter = ArrayAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                printerModelStringArray
            )
            spinnerPrinterModelFilter.adapter = printerFilterArrayAdapter

            spinnerPrinterModelFilter.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selectedPrinterModel = printerModelList[p2]
                        presenter.getCartridgeDep(selectedPrinterModel.id)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }

            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            buttonAddCartridge.setOnClickListener {
                if (dialogAddDartridgeSelectedCartridgeModel != null) {
                    if (textEditAmount.text!!.isEmpty()) {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Укажите количество")
                            setPositiveButton("Ok") { alertDialog, which ->
                                alertDialog.cancel()
                                alertDialog.dismiss()
                            }
                            create()
                            show()
                        }
                    } else {
                        presenter.addCartridgesToBaseState(
                            dialogAddDartridgeSelectedCartridgeModel?.id!!,
                            Integer.parseInt(textEditAmount.text.toString())
                        )
                        dialog.dismiss()
                    }
                } else {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Выберите модель картриджа")
                        setPositiveButton("Ok") { alertDialog, which ->
                            alertDialog.cancel()
                            alertDialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainStateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun updatePrinterModelList(list: List<PrinterModel>) {
        printerModelList = list
        printerModelStringArray.clear()
        printerModelList.forEach {
            printerModelStringArray.add(it.model)
        }
    }

    override fun updateCartridgeModelList(list: List<CartridgeModel>) {
        cartridgeModelList = list
    }

    override fun updateCartridgeAddDialogCartridgeList(list: List<CartridgeModel>) {
        dialogAddDartridgeSelectedCartridgeModel = null
        dialogAddCartridgeCartridgeList = list
        dialogAddCartridge?.let { dialog ->
            val spinnerCartridgeModel = dialog.findViewById<Spinner>(R.id.spinner_cartridge_model)
            val spinnerCartridgeModelAdapter = ArrayAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                dialogAddCartridgeCartridgeList.map {
                    it.model
                }
            )
            spinnerCartridgeModel.adapter = spinnerCartridgeModelAdapter
            spinnerCartridgeModel.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        dialogAddDartridgeSelectedCartridgeModel =
                            dialogAddCartridgeCartridgeList[p2]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
        }
    }

    override fun updateCartridgeList(list: List<CartridgeBaseStateModel>) {
        cartridgeListAdapter.clear()
        list.forEach {
            cartridgeListAdapter.add(ItemMainStateCartridge(it, this))
        }
    }

    override fun addCartridgeToBaseStateList(cartridge: CartridgeBaseStateModel) {
        cartridgeListAdapter.add(ItemMainStateCartridge(cartridge, this))
    }

    override fun showSuccessToast(message: String) {
        FancyToast.makeText(
            requireContext(),
            message,
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        ).show()
    }

    override fun MainStateItemClicked(cartridge: CartridgeBaseStateModel, position: Int) {

        val array: List<String> = listOf(
            "Взять",
            "Изменить количество",
            "Удалить",
            "Действие",
        )

        val adapter =
            SpinnerCustomAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                array
            )

        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_main_state_item_clicked)

        val spinnerActionList = dialog.findViewById<Spinner>(R.id.spinner_action_list)

        val itemAmountGroup = dialog.findViewById<Group>(R.id.item_amount_group)
        val textEditAmount = dialog.findViewById<TextInputEditText>(R.id.text_edit_amount)
        val buttonApplyNewAmount = dialog.findViewById<Button>(R.id.button_apply_new_amount)

        buttonApplyNewAmount.setOnClickListener {
            val newAmount = Integer.parseInt(textEditAmount.text.toString().trim())
            cartridge.amount = newAmount
            presenter.changeCartridgeAmount(cartridge.id, newAmount)
            cartridgeListAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }

        textEditAmount.setText(cartridge.amount.toString())

        spinnerActionList.adapter = adapter
        spinnerActionList.setSelection(spinnerActionList.count)


        spinnerActionList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 0) {
                        if (cartridge.amount > 0) {
                            Log.e("cartridge", cartridge.toString())
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Взять картридж?")
                                setPositiveButton("Ok") { alertDialog, which ->
                                    presenter.takeOneCartridge(cartridge.cartridge_id, cartridge.id)
                                    cartridge.amount -= 1;
                                    cartridgeListAdapter.notifyItemChanged(position)
                                    alertDialog.cancel()
                                    alertDialog.dismiss()
                                    dialog.dismiss()
                                }
                                setNegativeButton("No") { alertDialog, which ->
                                    alertDialog.cancel()
                                    alertDialog.dismiss()
                                }
                                create()
                                show()
                            }
                        } else {
                            AlertDialog.Builder(requireContext()).apply {
                                setTitle("Картриджей не осталось =(")
                                setPositiveButton("Ok") { alertDialog, which ->
                                    alertDialog.cancel()
                                    alertDialog.dismiss()
                                    dialog.dismiss()
                                }
                                create()
                                show()
                            }
                        }
                    }
                    if (p2 == 1) {
                        spinnerActionList.visibility = View.GONE
                        itemAmountGroup.visibility = View.VISIBLE
                    }
                    if (p2 == 2) {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Удалить?")
                            setPositiveButton("Ok") { alertDialog, which ->
                                cartridgeListAdapter.removeGroupAtAdapterPosition(position)
                                cartridgeListAdapter.notifyDataSetChanged()
                                presenter.deleteCartridgeFromBaseState(cartridge.id)
                                dialog.dismiss()
                                alertDialog.cancel()
                                alertDialog.dismiss()
                            }
                            setNegativeButton("No") { alertDialog, which ->
                                alertDialog.cancel()
                                alertDialog.dismiss()
                            }
                            create()
                            show()
                        }

                    }
                    spinnerActionList.setSelection(spinnerActionList.count)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }


        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }


}