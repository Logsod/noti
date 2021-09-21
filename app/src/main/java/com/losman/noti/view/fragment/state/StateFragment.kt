package com.losman.noti.view.fragment.state

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.StatesFragment
import com.losman.noti.databinding.FragmentStateBinding
import com.losman.noti.model.CartridgeStateModel
import com.losman.noti.view.adapter.SpinnerCustomAdapter
import com.xwray.groupie.GroupieAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

private const val ARG_PARAM1 = "STATE_ID"

class StateFragment : MvpAppCompatFragment(), StateView, ItemCartridgeStateListener {
    // TODO: Rename and change types of parameters
    private var stateId: Int? = null
    lateinit var binding: FragmentStateBinding

    private val cartridgeListAdapter = GroupieAdapter()

    @Inject
    lateinit var presenterProvider: Provider<StatePresenter>
    val presenter by moxyPresenter { presenterProvider.get() }
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            stateId = it.getInt(ARG_PARAM1)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_state, container, false)
        binding.recyclerViewCartridgeList.adapter = cartridgeListAdapter
        binding.recyclerViewCartridgeList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (stateId != null)
            presenter.getCartridgesByState(stateId!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            StateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun updateCartridgeList(list: List<CartridgeStateModel>) {
        cartridgeListAdapter.clear()
        list.forEach {
            cartridgeListAdapter.add(ItemCartridgeState(it, this))
        }
    }

    override fun stateItemClicked(item: CartridgeStateModel, position: Int) {
        val status_list = (parentFragment as StatesFragment).stateLabelList
        val filteredStatusList = status_list.filter {
            it.id != stateId
        }


        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_state_item_clicked)

        val array: List<String> = listOf(
            "Изменить статус",
            "Удалить",
            "Действие",
        )

        val actionAdapter =
            SpinnerCustomAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                array
            )

        val newStatusAdapter =
            ArrayAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                filteredStatusList.map { it.state_name }
            )

        val spinnerActionList = dialog.findViewById<Spinner>(R.id.spinner_action_list)
        val spinnerNewStatus = dialog.findViewById<Spinner>(R.id.spinner_new_status)
        val buttonApply = dialog.findViewById<Button>(R.id.button_apply)
        val cartridgeLabel = dialog.findViewById<TextView>(R.id.cartridge_label)
        spinnerActionList.adapter = actionAdapter
        spinnerActionList.setSelection(actionAdapter.count)

        spinnerNewStatus.adapter = newStatusAdapter
        cartridgeLabel.text = item.model

        spinnerActionList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 0) { //change status
                        spinnerActionList.visibility = View.GONE
                        spinnerNewStatus.visibility = View.VISIBLE
                        buttonApply.visibility = View.VISIBLE
                    }
                    if (p2 == 1) {//delete
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Удалить?")
                            setNegativeButton("No") { alertDialog, which ->
                                alertDialog.dismiss()
                                dialog.dismiss()
                            }
                            setPositiveButton("Yes") { alertDialog, which ->
                                presenter.deleteCartridgeState(item.row_state_id)
                                alertDialog.dismiss()
                                dialog.dismiss()
                                cartridgeListAdapter.removeGroupAtAdapterPosition(position)
                                cartridgeListAdapter.notifyDataSetChanged()
                            }
                            create()
                            show()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        buttonApply.setOnClickListener {
            presenter.changeCartridgeState(
                item.row_state_id,
                filteredStatusList[spinnerNewStatus.selectedItemPosition].id
            )
            dialog.dismiss()
            cartridgeListAdapter.removeGroupAtAdapterPosition(position)
            cartridgeListAdapter.notifyDataSetChanged()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}