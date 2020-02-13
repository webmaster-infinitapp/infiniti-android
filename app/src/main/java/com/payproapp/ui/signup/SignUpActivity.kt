package com.payproapp.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.ActivitySignUpBinding
import com.payproapp.model.state.RegisterState
import com.payproapp.ui.base.BaseWithFragmentActivity
import com.payproapp.ui.home.HomeActivity
import com.payproapp.ui.login.LoginActivity
import com.payproapp.ui.login.LoginUsernameFragment
import com.payproapp.ui.signup.register.CreatePasswordFragment
import com.payproapp.ui.signup.register.SignUpFragment
import com.payproapp.ui.signup.register.SmsValidationFragment
import com.payproapp.ui.signup.register.UsernameFragment
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class SignUpActivity : BaseWithFragmentActivity() {

    @Inject
    lateinit var executor: Scheduler
    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
        disposable.add(viewModel.stateOnBoarding
                               .subscribeOn(executor)
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(
                                       { response ->
                                           Timber.d("onNext: %s", response?.data?.registerState)
                                           response?.data?.let { data ->
                                               toNextScreen(data.registerState)
                                           }
                                       }, { error -> Timber.d("onError: %s", error) },
                                       {
                                           Timber.d("onComplete")
                                           HomeActivity.start(this)
                                       }))

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.sign_up_content, SignUpFragment.newInstance(), SignUpFragment::class.java.simpleName)
                .commit()
    }

    private fun toNextScreen(registerState: RegisterState) {
        when (registerState) {
            RegisterState.REGISTER -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.sign_up_content, SignUpFragment.newInstance(),
                                 SignUpFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
            }

            RegisterState.LOGIN -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.sign_up_content, LoginUsernameFragment.newInstance(),
                                 LoginUsernameFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
            }

            RegisterState.SMS_CODE -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.sign_up_content, SmsValidationFragment.newInstance(),
                                 SmsValidationFragment::class.java.simpleName)
                        .commit()
            }

            RegisterState.USERNAME -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.sign_up_content, UsernameFragment.newInstance(),
                                 UsernameFragment::class.java.simpleName)
                        .commit()
            }

            RegisterState.LOGIN_PASSWORD -> {
                LoginActivity.start(this, true)
            }

            RegisterState.PASSWORD -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.sign_up_content, CreatePasswordFragment.newInstance(),
                                 CreatePasswordFragment::class.java.simpleName)
                        .commit()
            }

            RegisterState.PASSWORD_VERIFIED -> {
                viewModel.verifyPassState()
            }


        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            this.finish()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}
