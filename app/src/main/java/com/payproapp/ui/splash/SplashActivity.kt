package com.payproapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.payproapp.R
import com.payproapp.databinding.ActivitySplashBinding
import com.payproapp.ui.base.BaseActivity
import com.payproapp.ui.login.LoginActivity
import com.payproapp.ui.signup.SignUpActivity
import com.payproapp.util.preferences.PreferencesManager
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        if (TextUtils.isEmpty(preferencesManager.getUserID())) {
            SignUpActivity.start(this)
        } else {
            LoginActivity.start(this, true)
            finish()
        }
    }

    override fun onBackPressed() {
    }
}
