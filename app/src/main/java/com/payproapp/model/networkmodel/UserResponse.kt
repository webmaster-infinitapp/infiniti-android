package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.payproapp.model.User

data class UserResponse(
        @Expose
        @SerializedName("results")
        val results: List<User>
)