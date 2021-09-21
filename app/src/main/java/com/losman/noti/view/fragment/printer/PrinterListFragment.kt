package com.losman.noti.view.fragment.printer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.textfield.TextInputEditText
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.FragmentPrinterListBinding
import com.losman.noti.model.AddPrinterRequest
import com.losman.noti.model.PrinterDevice
import com.losman.noti.model.PrinterModel
import com.losman.noti.view.adapter.SpinnerCustomAdapter
import com.xwray.groupie.GroupieAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class PrinterListFragment : MvpAppCompatFragment(), PrinterListView, PrinterItemListener {

    lateinit var binding: FragmentPrinterListBinding

    @Inject
    lateinit var presenterProvider: Provider<PrinterListPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var printerList: List<PrinterModel> = listOf()
    var modelNames: List<String> = listOf()
    private val printerAdapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_printer_list, container, false)

        presenter.getAllPrinterModel()

        binding.recyclerViewPrinterList.adapter = printerAdapter
        binding.recyclerViewPrinterList.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        )

        binding.buttonAddNewPrinter.setOnClickListener {

            val dialog = Dialog(requireView().context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_add_printer)


            val adapter =
                ArrayAdapter(
                    requireView().context,
                    android.R.layout.simple_spinner_dropdown_item,
                    modelNames
                )
            val spinner = dialog.findViewById<Spinner>(R.id.printer_item_model)
            spinner.adapter = adapter

            var selectedModel: PrinterModel? = null
            val printerComment = dialog.findViewById<TextInputEditText>(R.id.add_printer_comment)
            val addButton = dialog.findViewById<Button>(R.id.button_add_printer_to_printer_list)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedModel = printerList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


            addButton.setOnClickListener {
                selectedModel?.let {
                    presenter.addPrinter(
                        AddPrinterRequest(
                            model_id = selectedModel!!.id,
                            comment = printerComment.text.toString().trim()
                        )
                    )
                }
                dialog.dismiss()
            }


            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAllPrinter()

        binding.printerListRefresh.setOnRefreshListener {
            presenter.getAllPrinter()
            binding.printerListRefresh.isRefreshing = false
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun addPrinter(printerDevice: PrinterDevice) {
        printerAdapter.add(ItemPrinter(printerDevice, this))
    }

    override fun setPrinterModelList(list: List<PrinterModel>) {
        val names: MutableList<String> = mutableListOf()
        list.forEach {
            names.add(it.model)
        }
        modelNames = names
        printerList = list
    }

    override fun updatePrinterList(list: List<PrinterDevice>) {
        printerAdapter.clear()
        list.forEach {
            printerAdapter.add(ItemPrinter(it, this))
        }
    }

    override fun PrinterItemClicked(printer: PrinterDevice, position: Int) {
        showItemDialog(printer, position)
    }


    private fun showItemDialog(printer: PrinterDevice, position: Int) {

        val array: List<String> = listOf(
            "Изменить",
            "Удалить",
            "Действие",
        )


        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_printer)

        val adapter =
            SpinnerCustomAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                array
            )
        val spinner = dialog.findViewById<Spinner>(R.id.dropdown_list_view)

        val textViewPrinterModel = dialog.findViewById<TextView>(R.id.text_view_printer_model)
        val textViewPrinterComment =
            dialog.findViewById<TextView>(R.id.text_view_printer_comment)
        val printerItemModel = dialog.findViewById<TextView>(R.id.printer_item_model)
        val applyButton = dialog.findViewById<Button>(R.id.button_apply)
        val newComment = dialog.findViewById<TextInputEditText>(R.id.new_printer_comment)


        printerItemModel.text = printer.model
        newComment.setText(printer.comment)

        spinner.adapter = adapter
        spinner.setSelection(adapter.count)
        //var selectedModel : PrinterModel? = null
        //selectedModel = (printerModelAdapter.getItem(position) as ItemPrinterModel).model


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) { //if rename
                    textViewPrinterModel.visibility = View.VISIBLE
                    textViewPrinterComment.visibility = View.VISIBLE
                    printerItemModel.visibility = View.VISIBLE
                    applyButton.visibility = View.VISIBLE
                    newComment.visibility = View.VISIBLE
                }
                if (p2 == 1) { //if delete
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Удалить?")
                        setNegativeButton("No") { alertDialog, which ->
                            alertDialog.cancel()
                            alertDialog.dismiss()
                        }

                        setPositiveButton("Yes") { alertDialog, which ->
                            presenter.deletePrinter(printer)
                            printerAdapter.removeGroupAtAdapterPosition(position)
                            printerAdapter.notifyDataSetChanged()
                            alertDialog.cancel()
                            alertDialog.dismiss()
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                    spinner.setSelection(adapter.count)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        applyButton.setOnClickListener {
            printer.comment = newComment.text.toString().trim()
            presenter.updatePrinterComment(printer)
            printerAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
    }

}