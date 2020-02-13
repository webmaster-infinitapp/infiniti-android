package com.payproapp.ui.signup.register


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.payproapp.databinding.FragmentSignupBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.R
import com.payproapp.ui.signup.SignUpViewModel

class SignUpFragment : BaseFragment() {

    lateinit var binding: FragmentSignupBinding
    lateinit var viewModel: SignUpViewModel

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(SignUpViewModel::class.java)
            loadViewModel(viewModel, binding.root)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        setupUI(binding.root)
        setupListeners()
        setupCountryCodeWatcher(binding.tietCountryCode)

        binding.acceptListener = View.OnClickListener { checkForm() }
        binding.loginListener = View.OnClickListener { viewModel.loginState() }

        return binding.root
    }

    private fun checkForm() {
        var isError = false

        val name = binding.tietName.text.toString()
        val prefix = binding.tietCountryCode.text.toString()
        val phone = binding.tietPhone.text.toString()

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.error_required_field)
            isError = true
        }

        if (prefix.length <= 2) {
            binding.tilCountryCode.error = getString(R.string.error_required_field)
            isError = true
        } else if (checkIfNumber(prefix.substring(3))) {
            binding.tilCountryCode.error = getString(R.string.error_wrong_format)
            isError = true
        }

        if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.error_required_field)
            isError = true
        } else if (checkIfNumber(phone)) {
            binding.tilPhone.error = getString(R.string.error_wrong_format)
            isError = true
        }

        if (isError) {
            return
        }

        viewModel.sendOtp(name, prefix, phone)
    }

    private fun setupListeners() {
        clearErrorFromEditText(binding.tietName, binding.tilName)
        clearErrorFromEditText(binding.tietCountryCode, binding.tilCountryCode)
        clearErrorFromEditText(binding.tietPhone, binding.tilPhone)
    }

    private fun clearErrorFromEditText(editText: TextInputEditText, inputLayout: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                inputLayout.error = null
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun checkIfNumber(str: String): Boolean {
        val v = str.toIntOrNull()
        return when (v) {
            null -> true
            else -> false
        }
    }

    private fun setupCountryCodeWatcher(editText: TextInputEditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (editText.length() > 0) {
                    if (editText.text!![(editText.length() - 1)] == '+') {
                        editText.append(" ")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
