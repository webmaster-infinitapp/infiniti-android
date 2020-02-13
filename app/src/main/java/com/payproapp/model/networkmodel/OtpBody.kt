package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OtpBody(
        @SerializedName("telefono")
        @Expose
        private val phone: String,

        @SerializedName("codPais")
        @Expose
        private val codPais: String
)