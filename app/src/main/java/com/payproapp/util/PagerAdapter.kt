package com.payproapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.payproapp.ui.home.account.balances.BalancesFragment
import com.payproapp.ui.home.account.transactions.TransactionsFragment


class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return BalancesFragment.newInstance()
            1 -> return TransactionsFragment.newInstance()

            else -> return null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Balance"
            1 -> return "Transaction history"
            else -> return ""
        }
    }
}