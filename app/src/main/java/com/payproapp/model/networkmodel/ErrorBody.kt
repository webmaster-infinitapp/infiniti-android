package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorBody(
        @Expose
        @SerializedName("status")
        val status: Int,
        @Expose
        @SerializedName("error")
        val error: String,
        @Expose
        @SerializedName("message")
        val message: String)
