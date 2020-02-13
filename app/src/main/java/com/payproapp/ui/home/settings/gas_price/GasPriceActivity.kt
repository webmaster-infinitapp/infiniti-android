package com.payproapp.ui.home.settings.gas_price

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.ActivityGasPriceBinding
import com.payproapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.app_bar_default.toolbar


class GasPriceActivity : BaseActivity() {

    lateinit var binding: ActivityGasPriceBinding
    lateinit var viewModel: GasPriceViewModel

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, GasPriceActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gas_price)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GasPriceViewModel::class.java)
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
        title = getString(R.string.label_gas_price)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.other_activities, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.updateGasPrice()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
