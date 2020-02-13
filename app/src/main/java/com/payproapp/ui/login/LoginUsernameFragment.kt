package com.payproapp.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentLoginUsernameBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.signup.SignUpViewModel


class LoginUsernameFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginUsernameBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        @JvmStatic
        fun newInstance() = LoginUsernameFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_username, container, false)
        setupUI(binding.root)
        binding.registerListener = View.OnClickListener { viewModel.registerState() }
        binding.acceptListener = View.OnClickListener {
            checkUsername()
        }

        binding.tietUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkUsername()
                true
            } else {
                false
            }
        }
        return binding.root
    }

    private fun checkUsername() {
        if (TextUtils.isEmpty(binding.tietUsername.text.toString()))
            binding.tietUsername.error = getString(R.string.error_required_field)
        else
            viewModel.loginUsername(binding.tietUsername.text.toString())
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