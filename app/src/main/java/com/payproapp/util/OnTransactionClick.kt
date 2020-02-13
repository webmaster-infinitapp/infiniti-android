package com.payproapp.util

import com.payproapp.model.Transaction

interface OnTransactionClick {
    fun onTransactionClick(transaction: Transaction)
}