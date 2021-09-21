package com.losman.noti.view.mainview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import com.google.android.material.navigation.NavigationView

import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.losman.noti.*
import com.losman.noti.view.fragment.cartridge.CartridgesModelFragment
import com.losman.noti.view.fragment.printer.PrintersFragment
import com.losman.noti.view.login.LoginActivity
import java.lang.Exception


class MainViewActivity : MvpAppCompatActivity(), MainViewActivityView {

    @Inject
    lateinit var presenterProvider: Provider<MainViewActivityPresenter>
    val presenter by moxyPresenter { presenterProvider.get() }

    lateinit var mDrawer: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var nvDrawer: NavigationView

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private val drawerToggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout)


        nvDrawer = findViewById(R.id.nav_view)
        // Setup drawer view
        //setupDrawerContent(nvDrawer);


        var appBarConfiguration: AppBarConfiguration
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        nvDrawer.menu.findItem(R.id.logout)?.setOnMenuItemClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Выйти?")
                setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                    dialog.dismiss()
                }
                setPositiveButton("YES") { dialog, _ ->
                    presenter.logOut()
                    dialog.cancel()
                    dialog.dismiss()
                }
                show()
            }
            return@setOnMenuItemClickListener true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_cartridges, R.id.nav_printers, R.id.nav_states
            ), mDrawer
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nvDrawer.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.getItemId()) {
            android.R.id.home -> {
                mDrawer!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    override fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}