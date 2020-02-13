package com.payproapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Balance(
        @Expose
        @SerializedName("cuenta")
        val account: String,
        @Expose
        @SerializedName("descripcion")
        val description: String,
        @Expose
        @SerializedName("balance")
        val balance: String,
        @Expose
        @SerializedName("symbol")
        val symbol: String) {

    companion object {
        @JvmStatic
        fun fake() = Balance("0x1522900B6daFac587d499a862861C0869Be6E428",
                             "PayPro Token",
                             "2,500",
                             "PIP")
    }
}
