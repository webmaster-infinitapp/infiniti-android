package com.payproapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RandomUser(
        @Expose
        @SerializedName("title")
        val title: String,
        @Expose
        @SerializedName("first")
        val first: String,
        @Expose
        @SerializedName("last")
        val last: String
)