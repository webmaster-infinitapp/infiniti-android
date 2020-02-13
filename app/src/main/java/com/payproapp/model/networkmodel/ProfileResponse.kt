package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
        @Expose
        @SerializedName("nombre")
        val name: String,
        @Expose
        @SerializedName("gasPrice")
        val gasPrice: Double,
        @Expose
        @SerializedName("gasLimit")
        val gasLimit: Double,
        @Expose
        @SerializedName("imagen")
        val image: String)