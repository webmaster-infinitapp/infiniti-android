package com.payproapp.model.state

enum class SendState {
    SELECT_CONTACT,
    SET_ADDRESS,
    AMOUNT_ASSET,
    MESSAGE,
    CONFIRM,
    PROCESSING,
    FINAL
}