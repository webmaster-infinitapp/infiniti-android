package com.payproapp.model

import com.payproapp.model.state.RegisterState

class RegisterResponse private constructor(val loginState: RegisterState) {

    companion object {
        fun needRegister() = RegisterResponse(RegisterState.REGISTER)
        fun needSmsCode() = RegisterResponse(RegisterState.SMS_CODE)
    }
}