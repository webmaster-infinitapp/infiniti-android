package com.payproapp.ui.signup.register

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentCreatePasswordBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.signup.SignUpViewModel


class CreatePasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        @JvmStatic
        fun newInstance() = CreatePasswordFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_password, container, false)
        binding.inputPassword.filters[0] = InputFilter.LengthFilter(6)
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
        binding.inputPassword.requestFocus()
        showKeyBoard(binding.inputPassword)
    }
}