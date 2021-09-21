package com.losman.noti.view.login

import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.AppConstant
import com.losman.noti.view.AppConstant.Companion.SETTINGS_TOKEN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences
) :
    MvpPresenter<LoginActivityView>() {
    fun testApi(login: String, password: String) {
        notiApi.signIn(login, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showMessage(it.message)
                if (it.success) {
                    with(preferences.edit()) {
                        putString(AppConstant.SETTINGS_TOKEN, it.token)
                        apply()
                    }
                    viewState.startMainViewActivity()
                }
            }, {
                viewState.showMessage(it.message.toString())
            })
    }

}