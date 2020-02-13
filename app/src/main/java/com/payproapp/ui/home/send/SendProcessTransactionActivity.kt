package com.payproapp.ui.home.send

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.ActivitySendProcessTransactionBinding
import com.payproapp.model.networkmodel.OnSendBody
import com.payproapp.ui.base.BaseActivity


class SendProcessTransactionActivity : BaseActivity() {

    lateinit var binding: ActivitySendProcessTransactionBinding
    lateinit var viewModel: SendProcessTransactionViewModel

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, SendProcessTransactionActivity::class.java)
            context.startActivity(intent)
        }

        fun start(context: Context, onSendBody: OnSendBody) {
            val bundle = Bundle()
            bundle.putSerializable("onSendBody", onSendBody)

            val intent = Intent(context, SendProcessTransactionActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_process_transaction)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SendProcessTransactionViewModel::class.java)

        val onSendBody = intent.extras?.getSerializable("onSendBody") as OnSendBody

        viewModel.sendToken(onSendBody)

        viewModel.success.observe(this, Observer { success ->
            if (success) {
                SendFinishedTransactionActivity.start(this, onSendBody)
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                setResult(Activity.RESULT_FIRST_USER)
                finish()
            }
        })
    }

    override fun onBackPressed() {
    }
}
