package com.payproapp.ui.home.account.transactions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.payproapp.databinding.ActivityTransactionDetailsBinding
import com.payproapp.model.Transaction
import com.payproapp.ui.base.BaseActivity
import com.payproapp.R
import kotlinx.android.synthetic.main.app_bar_default.*


class TransactionDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityTransactionDetailsBinding
    lateinit var transaction: Transaction

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TransactionDetailsActivity::class.java)
            context.startActivity(intent)
        }

        fun start(context: Context, transaction: Transaction) {
            val bundle = Bundle()
            bundle.putSerializable("transaction", transaction)

            val intent = Intent(context, TransactionDetailsActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_details)
        transaction = intent.extras?.getSerializable("transaction") as Transaction
        binding.transaction = transaction

        binding.transactionDetailsDestinatary.setOnLongClickListener {
            copyToClipboard(transaction.destination)
            true
        }

        binding.transactionDetailsAmount.setOnLongClickListener {
            copyToClipboard(transaction.ether)
            true
        }

        binding.transactionDetailsHash.setOnLongClickListener {
            copyToClipboard(transaction.hashTransaction)
            true
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = getString(R.string.title_transaction_details)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copiedValue = ClipData.newPlainText("copied", text)
        clipboardManager.primaryClip = copiedValue

        Toast.makeText(this, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }
}
