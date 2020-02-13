package com.payproapp.ui.signup.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentUsernameBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.signup.SignUpViewModel


class UsernameFragment : BaseFragment() {

    private lateinit var binding: FragmentUsernameBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        @JvmStatic
        fun newInstance() = UsernameFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_username, container, false)
        setupUI(binding.root)
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
}