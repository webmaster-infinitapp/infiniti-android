package com.payproapp.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.databinding.ActivityLoginBinding
import com.payproapp.ui.base.BaseActivity
import com.payproapp.R
import com.payproapp.ui.home.HomeActivity


class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    private var initialScreen = true

    companion object {
        const val EXTRA_IS_LOGIN = "IS_LOGIN"

        fun start(context: Context, isInitialScreen: Boolean) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(EXTRA_IS_LOGIN, isInitialScreen)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        loadViewModel(viewModel, binding.root)
        binding.viewModel = viewModel

        binding.inputPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (initialScreen) {
                    viewModel.checkPasswordWithLogin(binding.inputPassword.text.toString())
                } else {
                    viewModel.checkPassword(binding.inputPassword.text.toString())
                }
                true
            } else {
                false
            }
        }

        binding.inputPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 6) {
                    if (initialScreen) {
                        viewModel.checkPasswordWithLogin(s.toString())
                    } else {
                        viewModel.checkPassword(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        getExtrasFromIntent()

        if (initialScreen) {
            viewModel.loginState()
        } else {
            viewModel.passwordState()
        }

        viewModel.success.observe(this, Observer { success ->
            success?.let {
                if (it) {
                    if (initialScreen) {
                        toHomeScreen()
                    } else {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
        })
    }

    private fun toHomeScreen() {
        hideKeyboard()
        finish()
        HomeActivity.start(this)
    }

    private fun getExtrasFromIntent() {
        initialScreen = intent.getBooleanExtra(EXTRA_IS_LOGIN, false)
    }
}
