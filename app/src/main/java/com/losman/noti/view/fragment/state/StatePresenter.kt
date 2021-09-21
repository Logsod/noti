package com.losman.noti.view.fragment.state

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class StatePresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<StateView>() {

    fun getCartridgesByState(id : Int){
        notiApi.getCartridgesByState(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateCartridgeList(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun changeCartridgeState(row_state_id : Int, state : Int){
        notiApi.changeCartridgeState(row_state_id,state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }

    fun deleteCartridgeState(row_state_id: Int){
        notiApi.deleteCartridgeState(row_state_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }
}