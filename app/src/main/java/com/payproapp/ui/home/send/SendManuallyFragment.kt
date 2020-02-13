package com.payproapp.ui.home.send


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.zxing.integration.android.IntentIntegrator
import com.payproapp.R
import com.payproapp.databinding.FragmentSendManuallyBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel


class SendManuallyFragment : BaseFragment() {

    private lateinit var binding: FragmentSendManuallyBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_send_manually, container, false)
        setupUI(binding.root)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            activity.title = getString(R.string.title_set_address)
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            binding.sendBody = viewModel.sendBody

            binding.setOnNextListener { checkForm() }
            binding.tietBeneficiary.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkForm()
                    true
                } else {
                    false
                }
            }

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.title_manually)))
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.title_scan_qr)))

            binding.tabLayout.addOnTabSelectedListener(
                    object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab) {
                            if (tab.position == 1) {
                                IntentIntegrator.forSupportFragment(this@SendManuallyFragment).initiateScan()
                                binding.tabLayout.getTabAt(0)?.select()
                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab) {

                        }

                        override fun onTabReselected(tab: TabLayout.Tab) {

                        }
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                //ERROR
            } else {
                if (!result.contents.isNullOrEmpty()) {
                    viewModel.sendBody.destinationKey = result.contents
                    viewModel.sendAmountState()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun checkForm() {
        if (viewModel.sendBody.destinationKey.isEmpty()) {
            binding.tietBeneficiary.error = getString(R.string.error_beneficiary_empty)
            return
        }

        hideKeyboard()
        viewModel.sendAmountState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendManuallyFragment()
    }
}
