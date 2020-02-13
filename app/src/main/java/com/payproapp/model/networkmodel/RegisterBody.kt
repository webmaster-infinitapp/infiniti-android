package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterBody(
        @Expose
        @SerializedName("idUsuario")
        val idUsuario: String = "",
        @Expose
        @SerializedName("nombre")
        val nombre: String = "",
        @Expose
        @SerializedName("codPais")
        val codPais: String = "",
        @Expose
        @SerializedName("telefono")
        val telefono: String = "",
        @Expose
        @SerializedName("password")
        val password: String = "",
        @Expose
        @SerializedName("alias")
        val alias: String = "") {

    companion object {
        @JvmStatic
        fun fake() = RegisterBody(
                "dgarciap",
                "dgarciap",
                "+34",
                "650123321", //change telephone to create new users
                "cGF5cHJv",
                "dgarciap")
    }
}

