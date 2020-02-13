package com.payproapp.ui.home.account.balances

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.ActivityAddNewTokenBinding
import com.payproapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.app_bar_default.toolbar


class AddNewTokenActivity : BaseActivity() {

    lateinit var binding: ActivityAddNewTokenBinding
    lateinit var viewModel: AddNewTokenViewModel

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, AddNewTokenActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_token)
        setupUI(binding.root)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddNewTokenViewModel::class.java)
        loadViewModel(viewModel, binding.root)
        binding.viewModel = viewModel

        viewModel.success.observe(this, Observer { success ->
            if (success) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = getString(R.string.title_add_token)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
