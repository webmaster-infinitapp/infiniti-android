package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.payproapp.model.state.RegisterState

data class OnboardingBody(
        @Expose
        @SerializedName("registerState")
        val registerState: RegisterState,
        @Expose
        @SerializedName("name")
        val name: String = "",
        @Expose
        @SerializedName("prefix")
        val prefix: String = "",
        @Expose
        @SerializedName("phone")
        val phone: String = ""
)