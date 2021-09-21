package com.losman.noti.view.fragment.state

import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject


class StatesPresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<StatesView>() {

    fun getAllStates(){
        notiApi.getStateList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateStateTab(it)
            }, {
                viewState.showToast(it.message ?: "request error")
            })
    }
}