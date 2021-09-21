package com.losman.noti.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.ActivityLoginBinding
import com.losman.noti.view.mainview.MainViewActivity
import com.losman.noti.view.settings.SettingsActivity
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginActivity : MvpAppCompatActivity(R.layout.activity_login), LoginActivityView {
    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var presenterProvider: Provider<LoginActivityPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonSignIn.setOnClickListener {
            presenter.testApi(
                binding.textViewLogin.text.toString(),
                binding.textViewPassword.text.toString()
            )
        }
        binding.textViewSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showMessage(message: String) {
        Log.e("TAG", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startMainViewActivity() {
        val intent = Intent(this, MainViewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}