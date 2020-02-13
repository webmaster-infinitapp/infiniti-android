package com.payproapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.payproapp.databinding.ActivityHomeBinding
import com.payproapp.model.networkmodel.OnSendBody
import com.payproapp.model.state.SendState
import com.payproapp.ui.base.BaseWithFragmentActivity
import com.payproapp.R
import com.payproapp.ui.home.account.AccountFragment
import com.payproapp.ui.home.receive.ReceiveFragment
import com.payproapp.ui.home.send.*
import com.payproapp.ui.home.settings.SettingsFragment
import com.payproapp.ui.home.settings.WebViewFragment
import com.payproapp.ui.home.settings.private_key.PrivateKeyFragment
import com.payproapp.ui.home.support.SupportFragment
import com.payproapp.ui.signup.SignUpActivity
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : BaseWithFragmentActivity(), NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var executor: Scheduler
    @Inject
    lateinit var preferencesManager: PreferencesManager

    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel

    private val sendBackStack = "sendBackStack"

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_account)
        navigateToHome()

        disposable.add(viewModel.stateOnSend
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            Timber.d("onNext: %s", response?.data?.sendState)
                            response?.data?.let { data ->
                                toNextSendScreen(data)
                            }
                        }, { error -> Timber.d("onError: %s", error) },
                        {
                            Timber.d("onComplete")
                        })
        )
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                this.finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate(
                    sendBackStack,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        when (item.itemId) {
            R.id.nav_account -> {
                navigateToHome()
            }

            R.id.nav_receive -> {
                navigateToReceive()
            }

            R.id.nav_support -> {
                navigateToSupport()
            }

            R.id.nav_settings -> {
                navigateToSettings()
            }

            R.id.nav_send -> {
                viewModel.sendSelectContactsState()
            }

            R.id.nav_logout -> {
                logout()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        preferencesManager.clearPreferences()
        SignUpActivity.start(this)
    }

    private fun toNextSendScreen(onSendBody: OnSendBody) {
        when (onSendBody.sendState) {

            SendState.SELECT_CONTACT -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.content_home,
                                SelectContactFragment.newInstance(),
                                SelectContactFragment::class.java.simpleName
                        )
                        .addToBackStack(sendBackStack)
                        .commit()
            }

            SendState.SET_ADDRESS -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.content_home,
                                SendManuallyFragment.newInstance(),
                                SendManuallyFragment::class.java.simpleName
                        )
                        .addToBackStack(sendBackStack)
                        .commit()
            }

            SendState.AMOUNT_ASSET -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.content_home,
                                SendAmountFragment.newInstance(),
                                SendAmountFragment::class.java.simpleName
                        )
                        .addToBackStack(sendBackStack)
                        .commit()
            }

            SendState.MESSAGE -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.content_home,
                                SendMessageFragment.newInstance(),
                                SendMessageFragment::class.java.simpleName
                        )
                        .addToBackStack(sendBackStack)
                        .commit()
            }

            SendState.CONFIRM -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.content_home,
                                SendConfirnTransFragment.newInstance(),
                                SendConfirnTransFragment::class.java.simpleName
                        )
                        .addToBackStack(sendBackStack)
                        .commit()
            }

            SendState.PROCESSING -> {
                SendProcessTransactionActivity.start(this, onSendBody)
            }
        }
    }

    fun removeAllBackStack() {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    fun navigateToPrivateKey() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        PrivateKeyFragment.newInstance(),
                        PrivateKeyFragment::class.java.simpleName
                )
                .addToBackStack(sendBackStack)
                .commit()
    }

    fun navigateToWebView(name: String, url: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        WebViewFragment.newInstance(name, url),
                        WebViewFragment::class.java.simpleName
                )
                .addToBackStack(sendBackStack)
                .commit()
    }

    fun navigateToHome() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        AccountFragment.newInstance(),
                        AccountFragment::class.java.simpleName
                )
                .commit()
    }

    private fun navigateToSupport() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        SupportFragment.newInstance(),
                        SupportFragment::class.java.simpleName
                )
                .addToBackStack(sendBackStack)
                .commit()
    }

    private fun navigateToSettings() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        SettingsFragment.newInstance(),
                        SettingsFragment::class.java.simpleName
                )
                .addToBackStack(sendBackStack)
                .commit()
    }

    private fun navigateToReceive() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_home,
                        ReceiveFragment.newInstance(),
                        ReceiveFragment::class.java.simpleName
                )
                .addToBackStack(sendBackStack)
                .commit()
    }
}
