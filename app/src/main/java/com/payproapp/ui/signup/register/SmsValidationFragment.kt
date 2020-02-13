package com.payproapp.ui.signup.register

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentSmsValidationBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.signup.SignUpViewModel
import timber.log.Timber


class SmsValidationFragment : BaseFragment() {

    private lateinit var binding: FragmentSmsValidationBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        fun newInstance() = SmsValidationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms_validation, container, false)
        binding.inputSmsPassword.filters[0] = InputFilter.LengthFilter(4)
        binding.root.setOnClickListener {
            val prefs = activity?.getSharedPreferences("mypref", MODE_PRIVATE)
            Timber.d("guardado: ${prefs?.getString("hola", "vacio")}")
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(SignUpViewModel::class.java)
            loadViewModel(viewModel, binding.root)
            binding.viewModel = viewModel
        }
    }

    override fun onResume() {
        super.onResume()
        binding.inputSmsPassword.requestFocus()
        showKeyBoard(binding.inputSmsPassword)
    }
}