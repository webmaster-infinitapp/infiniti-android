package com.payproapp.ui.home.account.balances

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.payproapp.R
import com.payproapp.databinding.ItemBalanceBinding
import com.payproapp.model.Balance

class BalanceAdapter : RecyclerView.Adapter<BalanceAdapter.Companion.BalanceViewHolder>() {
    private var balances: MutableList<Balance> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val bindingBalance = DataBindingUtil.inflate<ItemBalanceBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_balance,
                parent,
                false)

        return BalanceViewHolder(bindingBalance)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(balances[position])
    }

    override fun getItemCount(): Int {
        return balances.size
    }

    companion object {
        class BalanceViewHolder(private val binding: ItemBalanceBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(balance: Balance) {
                binding.balance = balance
            }
        }
    }

    fun clear() {
        balances.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<Balance>) {
        balances.clear()
        balances.addAll(list)
        notifyDataSetChanged()
    }
}