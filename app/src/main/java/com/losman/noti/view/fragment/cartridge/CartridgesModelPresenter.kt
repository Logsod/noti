package com.losman.noti.view.fragment.cartridge

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.retrofit.NotiService
import com.losman.noti.retrofit.request.RequestDeleteCartridgeModels
import com.losman.noti.retrofit.request.RequestUpdateCartridgeDep
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class CartridgesModelPresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<CartridgesModelView>() {
    fun addCartridgeModel(cartridgeModelName: String) {
        notiApi.addCartridgeModel(cartridgeModelName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.addCartridgeModel(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun getAllCartridgeModel() {
        notiApi.getAllCartridgeModel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateCartridgeModelList(it)
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

    fun updateCartridgeModelDep(
        cartridgesList: List<Int>,
        printersList: List<Int>,
    ) {
        notiApi.updateCartridgeModelDep(RequestUpdateCartridgeDep(cartridgesList, printersList))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateCartridgeModelList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun clearCartridgeModelDep(
        cartridgeList: List<Int>
    ) {
        notiApi.updateCartridgeModelDep(RequestUpdateCartridgeDep(cartridgeList, listOf(), true))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("tag",it.toString())
                viewState.updateCartridgeModelList(it)
            },{
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun deleteCartridgeModel(id : List<Int>){
        notiApi.deleteCartridgeModel(RequestDeleteCartridgeModels(id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.removeCartridgeModelsFromList(id)
            },{
                viewState.showToast(it.message ?: "request error")
            })
    }
}