package com.losman.noti.view.mainview

import android.content.Context
import android.content.SharedPreferences
import android.util.AndroidException
import android.util.JsonToken
import android.util.Log
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.AppConstant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class MainViewActivityPresenter
@Inject constructor(
    val context: Context,
    val notiService: NotiService,
    val preferences: SharedPreferences
) : MvpPresenter<MainViewActivityView>() {

    fun logOut() {
        val token = preferences.getString(AppConstant.SETTINGS_TOKEN, "")
        notiService.logOut(token ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.startLoginActivity()
            }, {
            })
    }
}