package com.payproapp.ui.home.settings.change_password

import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentChangePasswordBinding
import com.payproapp.ui.base.BaseFragment


class ChangePasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)
        binding.inputPassword.filters[0] = InputFilter.LengthFilter(6)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ChangePasswordViewModel::class.java)
            binding.viewModel = viewModel

            viewModel.success.observe(this, Observer { success ->
                if (success) {
                    activity.setResult(Activity.RESULT_OK)
                    activity.finish()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        binding.inputPassword.requestFocus()
        showKeyBoard(binding.inputPassword)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangePasswordFragment()
    }
}