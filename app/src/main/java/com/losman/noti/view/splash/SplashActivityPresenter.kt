package com.losman.noti.view.splash

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.losman.noti.retrofit.NotiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class SplashActivityPresenter //@Inject constructor
    (
    var context: Context,
    var notiService: NotiService,
    var preferences: SharedPreferences
) : MvpPresenter<SplashActivityView>() {

    fun checkToken() {
        val token = preferences.getString("token", "")
        if (token.isNullOrEmpty()) {
            viewState.startLoginActivity()
        } else {
            notiService.checkToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.tokenIsValid)
                        viewState.startLoginActivity()
                    else
                        viewState.startMainViewActivity()
                }, {
                    viewState.connectionError(it.message ?: "request error")
                })
        }
    }
}