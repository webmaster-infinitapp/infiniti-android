package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddTokenBody(
        @Expose
        @SerializedName("smartContratAddress")
        val smartContratAddress: String,
        @Expose
        @SerializedName("tokenSymbol")
        val tokenSymbol: String,
        @Expose
        @SerializedName("decimals")
        val decimals: String,

        @Expose
        @SerializedName("password")
        val password: String,
        @Expose
        @SerializedName("tokenName")
        val tokenName: String)
