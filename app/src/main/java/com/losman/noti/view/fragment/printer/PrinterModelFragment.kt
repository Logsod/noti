package com.losman.noti.view.fragment.printer

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.losman.noti.databinding.FragmentPrinterModelBinding
import com.losman.noti.model.PrinterModel
import com.losman.noti.view.adapter.SpinnerCustomAdapter
import com.xwray.groupie.GroupieAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class PrinterModelFragment : MvpAppCompatFragment(), PrinterModelView, PrinterModelItemListener {

    lateinit var binding: FragmentPrinterModelBinding


    @Inject
    lateinit var presenterProvider: Provider<PrinterModelPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    val printerModelAdapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_printer_model, container, false)
        //return inflater.inflate(R.layout.fragment_printer_model, container, false)

        binding.button.setOnClickListener {
            if (binding.newPrinterName.text?.isNotEmpty() == true) {
                presenter.addPrinterModel(binding.newPrinterName.text.toString().trim())
                binding.newPrinterName.text?.clear()
            }
        }
        binding.printerModelList.adapter = printerModelAdapter
        binding.printerModelList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAllPrinterModel()
    }

    override fun showMessage() {
        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun updatePrinterModelList(models: List<PrinterModel>) {
        printerModelAdapter.clear()
        models.forEach {
            printerModelAdapter.add(ItemPrinterModel(it, this))
        }
        printerModelAdapter.notifyDataSetChanged()
    }

    override fun printerModelItemClicked(model: PrinterModel, position: Int) {
        Log.e("click", model.toString())
        showItemDialog(model, position)
    }

    private fun showItemDialog(model: PrinterModel, position: Int) {

        val array: List<String> = listOf(
            "Переименовать",
            "Удалить",
            "Действие",
        )


        val dialog = Dialog(requireView().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_printer_model)

        val adapter =
            SpinnerCustomAdapter(
                requireView().context,
                android.R.layout.simple_spinner_dropdown_item,
                array
            )
        val spinner = dialog.findViewById<Spinner>(R.id.dropdown_list_view)

        val apply_new_name = dialog.findViewById<Button>(R.id.button_apply_new_name)
        val new_name = dialog.findViewById<TextInputEditText>(R.id.label_text_view)
        val text_model_name = dialog.findViewById<TextView>(R.id.text_model_name)

        text_model_name.text = model.model
        spinner.adapter = adapter
        spinner.setSelection(adapter.count)
        //var selectedModel : PrinterModel? = null
        //selectedModel = (printerModelAdapter.getItem(position) as ItemPrinterModel).model


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) { //if rename
                    apply_new_name.visibility = View.VISIBLE
                    new_name.visibility = View.VISIBLE
                }
                if (p2 == 1) { //if delete
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Удалить?")
                        setNegativeButton("No") { alertDialog, which ->
                            alertDialog.cancel()
                            alertDialog.dismiss()
                        }

                        setPositiveButton("Yes") { alertDialog, which ->
                            presenter.deletePrinterModelName(model)
                            printerModelAdapter.removeGroupAtAdapterPosition(position)
                            printerModelAdapter.notifyDataSetChanged()
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

        apply_new_name.setOnClickListener {
            model.model = new_name.text.toString().trim()
            presenter.updatePrinterModelName(model)
            printerModelAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
    }


}