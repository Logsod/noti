package com.losman.noti.view.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.ActivitySplashBinding
import com.losman.noti.retrofit.HostSelectionInterceptor
import com.losman.noti.retrofit.RetrofitApi
import com.losman.noti.view.AppConstant
import com.losman.noti.view.login.LoginActivity
import com.losman.noti.view.mainview.MainViewActivity
import com.losman.noti.view.settings.SettingsActivity
import com.shashank.sony.fancytoastlib.FancyToast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

class SplashActivity : MvpAppCompatActivity(), SplashActivityView {

        @Inject
    lateinit var presenterProvider: Provider<SplashActivityPresenter>
    val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)


    }

    override fun onResume() {
        super.onResume()
        presenter.checkToken()
        binding.buttonConnect.setOnClickListener {
            presenter.checkToken()
            binding.progressLoader.visibility = View.VISIBLE

            binding.buttonConnect.visibility = View.GONE
            binding.buttonSettings.visibility = View.GONE
        }

        binding.buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun startLoginActivity() {
        Log.e("TAG", "startLoginActivity")
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun startMainViewActivity() {
        val intent = Intent(this, MainViewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showErrorToast(message: String) {
        FancyToast.makeText(
            applicationContext,
            message,
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR,
            false
        ).show()

    }


    override fun showRetryButton() {
        binding.progressLoader.visibility = View.GONE
        binding.buttonConnect.visibility = View.VISIBLE
        binding.buttonSettings.visibility = View.VISIBLE

    }

    override fun connectionError(errorMessage: String) {
        showErrorToast(errorMessage)
        showRetryButton()
    }
}