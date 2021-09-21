package com.losman.noti.view.fragment.state

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class MainStatePresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<MainStateView>() {

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

    fun getCartridgeDep(id: Int) {
        notiApi.getCartridgeDep(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateCartridgeAddDialogCartridgeList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })

    }

    fun addCartridgesToBaseState(cartridgeModelId: Int, amount: Int) {
        notiApi.addCartridgesToBaseState(cartridgeModelId, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.addCartridgeToBaseStateList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun getAllBaseStateCartridges() {
        notiApi.getAllBaseStateCartridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateCartridgeList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun changeCartridgeAmount(cartridge_id: Int, amount: Int) {
        notiApi.changeCartridgeAmount(cartridge_id, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun deleteCartridgeFromBaseState(cartridge_id: Int) {
        notiApi.deleteCartridgeFromBaseState(cartridge_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun takeOneCartridge(cartridge_id: Int, id: Int) {
        notiApi.takeOneCartridge(cartridge_id, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

}