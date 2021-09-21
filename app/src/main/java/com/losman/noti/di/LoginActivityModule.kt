package com.losman.noti.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.losman.noti.retrofit.HostSelectionInterceptor
import com.losman.noti.retrofit.NotiService
import com.losman.noti.retrofit.RetrofitApi
import com.losman.noti.view.AppConstant
import com.losman.noti.view.login.LoginActivityPresenter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import javax.inject.Singleton
import okhttp3.OkHttpClient




@Module
class LoginActivityModule() {

    @Provides
    @Singleton
    fun provideLoginPresenter(
        context: Context,
        notiService: NotiService,
        preferences: SharedPreferences
    ): LoginActivityPresenter {
        return LoginActivityPresenter(context, notiService, preferences)
    }


}
