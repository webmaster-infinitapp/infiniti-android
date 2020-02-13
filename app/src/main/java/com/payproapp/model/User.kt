package com.payproapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
        @Expose
        @SerializedName("email")
        val email: String,
        @Expose
        @SerializedName("name")
        val name: RandomUser
)