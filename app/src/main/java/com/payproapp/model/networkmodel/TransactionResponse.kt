package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.payproapp.model.Transaction

data class TransactionResponse(
        @Expose
        @SerializedName("paginaActual")
        val currentPage: String,
        @Expose
        @SerializedName("paginaFinal")
        val finalPage: String,
        @Expose
        @SerializedName("transacciones")
        val transactions: MutableList<Transaction>)