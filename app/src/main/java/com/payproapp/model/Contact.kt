package com.payproapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Contact(
        val name: String,
        @SerializedName("telefono")
        @Expose
        val phone: String,
        @SerializedName("cuenta")
        @Expose
        var account: String)