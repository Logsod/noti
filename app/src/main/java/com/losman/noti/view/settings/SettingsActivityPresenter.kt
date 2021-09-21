package com.losman.noti.view.settings

import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.NotiService
import com.losman.noti.view.AppConstant
import moxy.MvpPresenter
import javax.inject.Inject

class SettingsActivityPresenter @Inject constructor(
    var context: Context,
    var notiApi: NotiService,
    var preferences: SharedPreferences,
) : MvpPresenter<SettingsActivityView>() {
    fun getServerSettings() {

        viewState.setupServerFields(
            preferences.getString(AppConstant.SETTINGS_SERVER_IP, "") ?: "",
            preferences.getString(AppConstant.SETTINGS_SERVER_PORT, "") ?: ""
        )
    }

    fun setServerSettings(serverIp: String, serverPort: String) {
        with(preferences.edit()) {
            putString(AppConstant.SETTINGS_SERVER_IP, serverIp)
            putString(AppConstant.SETTINGS_SERVER_PORT, serverPort)
            apply()
        }
    }
}