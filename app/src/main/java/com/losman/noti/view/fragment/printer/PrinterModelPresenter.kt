package com.losman.noti.view.fragment.printer

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.model.PrinterModel
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class PrinterModelPresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<PrinterModelView>() {
    fun testCall() {
        viewState.showMessage()
        Log.e("tagg", "test from test call")
    }

    fun addPrinterModel(printerModelName: String) {
        notiApi.addPrinterModel(printerModelName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun getAllPrinterModel() {
        notiApi.getAllPrinterModel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updatePrinterModelList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun updatePrinterModelName(model: PrinterModel) {
        notiApi.updatePrinterModelName(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun deletePrinterModelName(model: PrinterModel) {
        notiApi.deletePrinterModelName(model.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                }, {
                    viewState.showToast(it.message ?: "request error")
                }
            )
    }
}