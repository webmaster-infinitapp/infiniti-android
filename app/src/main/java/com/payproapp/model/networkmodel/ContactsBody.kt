package com.payproapp.model.networkmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContactsBody(
        @Expose
        @SerializedName("phonesArray")
        val phonesArray: Array<String>)