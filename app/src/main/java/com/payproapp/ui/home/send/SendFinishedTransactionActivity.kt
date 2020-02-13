package com.payproapp.ui.home.send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.payproapp.R
import com.payproapp.databinding.ActivitySendFinishedBinding
import com.payproapp.model.networkmodel.OnSendBody
import com.payproapp.ui.base.BaseActivity


class SendFinishedTransactionActivity : BaseActivity() {
    lateinit var binding: ActivitySendFinishedBinding

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, SendFinishedTransactionActivity::class.java)
            context.startActivity(intent)
        }

        fun start(context: Context, onSendBody: OnSendBody) {
            val bundle = Bundle()
            bundle.putSerializable("onSendBody", onSendBody)

            val intent = Intent(context, SendFinishedTransactionActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_finished)

        val onSendBody = intent.extras?.getSerializable("onSendBody") as OnSendBody
        onSendBody.let { binding.sendBody = it }
    }
}
