package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionBody(
        @Expose
        @SerializedName("descripcion")
        private val descripcion: String,
        @Expose
        @SerializedName("valor")
        private val valor: String,
        @Expose
        @SerializedName("destino")
        private val destino: String,

        @Expose
        @SerializedName("password")
        private val password: String)