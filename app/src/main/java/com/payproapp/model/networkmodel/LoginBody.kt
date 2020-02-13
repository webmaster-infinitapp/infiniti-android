package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.payproapp.BuildConfig

data class LoginBody(
        @Expose
        @SerializedName("username")
        private val username: String,
        @Expose
        @SerializedName("password")
        private val password: String) {

    companion object {
        @JvmStatic
        fun fake() = LoginBody(BuildConfig.FAKE_USERNAME, BuildConfig.FAKE_PASSWORD)
    }
}