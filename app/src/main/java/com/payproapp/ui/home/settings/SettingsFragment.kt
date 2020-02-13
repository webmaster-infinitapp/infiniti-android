package com.payproapp.ui.home.settings


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.payproapp.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.databinding.FragmentSettingsBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeActivity
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.ui.home.settings.change_password.ChangePasswordActivity
import com.payproapp.ui.home.settings.gas_limit.GasLimitActivity
import com.payproapp.ui.home.settings.gas_price.GasPriceActivity
import com.payproapp.ui.login.LoginActivity
import com.payproapp.util.preferences.PreferencesManager
import javax.inject.Inject


/**
 * A simple [BaseFragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    private val CONFIRM_PASSWORD_REQUEST = 1
    private val PASSWORD_CHANGE_REQUEST = 2
    private val GAS_PRICE_REQUEST = 3
    private val GAS_LIMIT_REQUEST = 4

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.passwordListener = View.OnClickListener {
            activity?.let { activity ->
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                this.startActivityForResult(intent, PASSWORD_CHANGE_REQUEST)
            }
        }

        binding.privateKeysListener = View.OnClickListener {
            activity?.let {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.putExtra(LoginActivity.EXTRA_IS_LOGIN, false)
                this.startActivityForResult(intent, CONFIRM_PASSWORD_REQUEST)
            }
        }
        binding.gasPriceListener = View.OnClickListener {
            activity?.let { activity ->
                val intent = Intent(activity, GasPriceActivity::class.java)
                this.startActivityForResult(intent, GAS_PRICE_REQUEST)
            }
        }
        binding.gasLimitListener = View.OnClickListener {
            activity?.let { activity ->
                val intent = Intent(activity, GasLimitActivity::class.java)
                this.startActivityForResult(intent, GAS_LIMIT_REQUEST)
            }
        }
        binding.aboutUsListener = View.OnClickListener {
            activity?.let {
                (activity as HomeActivity).navigateToWebView(getString(R.string.label_about_us),
                        getString(R.string.link_about_us))
            }
        }
        binding.rateUsListener = View.OnClickListener {
            activity?.let {
                (activity as HomeActivity).navigateToWebView(getString(R.string.label_rates_us),
                        getString(R.string.link_website))
            }
        }
        binding.infoListener = View.OnClickListener {
            activity?.let {
                (activity as HomeActivity).navigateToWebView(getString(R.string.label_info),
                        getString(R.string.link_privacy_policy))
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)

            when (requestCode) {
                CONFIRM_PASSWORD_REQUEST -> {
                    (activity as HomeActivity).navigateToPrivateKey()
                }
                PASSWORD_CHANGE_REQUEST -> {
                    Toast.makeText(context, getString(R.string.message_password_changed), Toast.LENGTH_SHORT).show()
                }
                GAS_PRICE_REQUEST -> {
                    Toast.makeText(context, getString(R.string.message_gas_price_changed), Toast.LENGTH_SHORT).show()
                }
                GAS_LIMIT_REQUEST -> {
                    Toast.makeText(context, getString(R.string.message_gas_limit_changed), Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            activity.title = getString(R.string.title_settings)
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            binding.viewModel = viewModel
            viewModel.retrieveUserProfile()

            viewModel.profileInfo.observe(this, Observer {
                setGasLimitAndPrice()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        setGasLimitAndPrice()
    }

    private fun setGasLimitAndPrice() {
        preferencesManager.getGasPrice()?.let {
            binding.gasPrice = (it / 1000).toString()
        }

        preferencesManager.getGasLimit()?.let {
            binding.gasLimit = it.toString()
        }

        binding.settingsGasLimitValue.visibility = View.VISIBLE
        binding.settingsGasPriceValue.visibility = View.VISIBLE
    }

    private fun openChromeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")

        try {
            context?.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null)
            context?.startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ReceiveFragment.
         */
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
