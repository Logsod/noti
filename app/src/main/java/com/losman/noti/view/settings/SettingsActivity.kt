package com.losman.noti.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.losman.noti.App
import com.losman.noti.R
import com.losman.noti.databinding.ActivitySettingsBinding
import com.losman.noti.di.LoginActivityModule
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SettingsActivity : MvpAppCompatActivity(), SettingsActivityView {

    @Inject
    lateinit var presenterProvider: Provider<SettingsActivityPresenter>
    val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.buttonApply.setOnClickListener {
            presenter.setServerSettings(
                binding.textEditServerIp.text.toString().trim(),
                binding.textEditServerPort.text.toString().trim()
            )

        }
        binding.textViewBackLabel.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getServerSettings()
    }

    override fun setupServerFields(ip: String, port: String) {
        binding.textEditServerIp.setText(ip)
        binding.textEditServerPort.setText(port)
    }
}