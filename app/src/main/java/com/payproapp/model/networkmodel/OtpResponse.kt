package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OtpResponse(
        @SerializedName("num")
        @Expose
        private val num: String,
        @SerializedName("desc")
        @Expose
        private val desc: String
)