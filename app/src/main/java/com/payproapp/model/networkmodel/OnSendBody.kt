package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.payproapp.model.state.SendState
import java.io.Serializable

data class OnSendBody(
        @Expose
        @SerializedName("sendState")
        var sendState: SendState,
        @Expose
        @SerializedName("name")
        var name: String = "",
        @Expose
        @SerializedName("prefix")
        var prefix: String = "",
        @Expose
        @SerializedName("phone")
        var phone: String = "",

        @Expose
        @SerializedName("description")
        var description: String = "",
        @Expose
        @SerializedName("amount")
        var amount: String = "",
        @Expose
        @SerializedName("destinationKey")
        var destinationKey: String = "",
        @Expose
        @SerializedName("originKey")
        var originKey: String = "",
        @Expose
        @SerializedName("tokenSymbol")
        var tokenSymbol: String = ""
) : Serializable