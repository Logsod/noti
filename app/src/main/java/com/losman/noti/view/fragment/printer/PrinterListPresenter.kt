package com.losman.noti.view.fragment.printer

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.model.AddPrinterRequest
import com.losman.noti.model.PrinterDevice
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class PrinterListPresenter
@Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<PrinterListView>() {


    fun getAllPrinterModel() {
        notiApi.getAllPrinterModel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setPrinterModelList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun addPrinter(printer: AddPrinterRequest) {
        notiApi.addPrinter(printer.model_id, printer.comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.addPrinter(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun getAllPrinter() {
        notiApi.getAllPrinter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updatePrinterList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun updatePrinterComment(printer: PrinterDevice) {
        notiApi.updatePrinterComment(printer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun deletePrinter(printer: PrinterDevice){
        notiApi.deletePrinter(printer.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{
                viewState.showToast(it.message ?: "request error")
            })
    }
}