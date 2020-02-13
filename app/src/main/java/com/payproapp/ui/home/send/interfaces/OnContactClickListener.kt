package com.payproapp.ui.home.send.interfaces

import com.payproapp.model.Contact

interface OnContactClickListener {

    fun onContactClicked(contact: Contact)

    fun onEmptyContactClicked()
}