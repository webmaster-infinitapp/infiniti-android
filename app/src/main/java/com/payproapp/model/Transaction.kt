package com.payproapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Transaction(
        @Expose
        @SerializedName("hasTransaccion")
        val hashTransaction: String,
        @Expose
        @SerializedName("numBloque")
        val num: Int,
        @Expose
        @SerializedName("fecha")
        val date: String,
        @Expose
        @SerializedName("origen")
        val origin: String,
        @Expose
        @SerializedName("tipo")
        val type: String,
        @Expose
        @SerializedName("destino")
        val destination: String,
        @SerializedName("mensaje")
        val message: String,
        @Expose
        @SerializedName("ether")
        val ether: String,
        @Expose
        @SerializedName("txFee")
        val fee: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("decimal")
        val decimal: String) : Serializable
