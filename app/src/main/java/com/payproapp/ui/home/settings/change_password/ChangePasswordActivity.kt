package com.payproapp.ui.home.settings.change_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.ActivityChangePasswordBinding
import com.payproapp.ui.base.BaseWithFragmentActivity


class ChangePasswordActivity : BaseWithFragmentActivity() {
    lateinit var binding: ActivityChangePasswordBinding
    lateinit var viewModel: ChangePasswordViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChangePasswordViewModel::class.java)
        loadViewModel(viewModel, binding.root)

        loadChangePasswordFragment()
    }

    fun loadChangePasswordFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.content_frame,
                        ChangePasswordFragment.newInstance(),
                        ChangePasswordFragment::class.java.simpleName
                )
                .commit()
    }
}
