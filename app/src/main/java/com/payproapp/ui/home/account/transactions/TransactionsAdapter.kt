package com.payproapp.ui.home.account.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.payproapp.R
import com.payproapp.databinding.ItemTransactionBinding
import com.payproapp.model.Transaction
import com.payproapp.util.OnTransactionClick

class TransactionsAdapter(
        private val onTransactionClick: OnTransactionClick) : RecyclerView.Adapter<TransactionsAdapter.Companion.TransactionViewHolder>() {
    private var trasanctions: MutableList<Transaction> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val bindingTransaction = DataBindingUtil.inflate<ItemTransactionBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_transaction,
                parent,
                false)

        return TransactionViewHolder(bindingTransaction, onTransactionClick)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(trasanctions[position])
    }

    override fun getItemCount(): Int {
        return trasanctions.size
    }

    companion object {
        class TransactionViewHolder(private val binding: ItemTransactionBinding,
                                    private val onTransactionClick: OnTransactionClick) : RecyclerView.ViewHolder(
                binding.root) {
            fun bind(transaction: Transaction) {
                binding.transaction = transaction
                binding.onTransactionClick = View.OnClickListener {
                    onTransactionClick.onTransactionClick(transaction)
                }
            }
        }
    }

    fun clear() {
        trasanctions.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<Transaction>) {
        trasanctions.clear()
        trasanctions.addAll(list)
        notifyDataSetChanged()
    }
}