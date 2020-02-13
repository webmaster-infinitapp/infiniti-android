package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
        @Expose
        @SerializedName("password")
        val password: String = "",
        @Expose
        @SerializedName("newPassword")
        val newPassword: String = "")

