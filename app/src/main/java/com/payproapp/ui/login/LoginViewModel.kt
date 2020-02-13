package com.payproapp.ui.login

import androidx.databinding.ObservableField
import com.payproapp.domain.interactor.LoginInteractor
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import com.payproapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginInteractor: LoginInteractor,
                                         private val executor: Scheduler,
                                         private val preferencesManager: PreferencesManager) : BaseViewModel() {

    val infoText: ObservableField<Int> = ObservableField(R.string.message_login)
    val password: ObservableField<String> = ObservableField("")

    init {
        success.value = false
    }

    fun loginState() {
        infoText.set(R.string.message_login)
    }

    fun passwordState() {
        infoText.set(R.string.message_confirm_password)
    }

    fun checkPasswordWithLogin(password: String) {
        val pin = password

        if (pin.length == 6) {
            if (!PreferencesManager.username.isEmpty()) {
                login(PreferencesManager.username, pin)
            } else {
                preferencesManager.getUserID()?.let {
                    login(it, pin)
                }
            }
        } else {
            onPinError()
        }
    }

    fun checkPassword(passwordText: String) {
        val pin = passwordText

        if (pin.length == 6) {
            if (preferencesManager.getPasswordPin() == pin) {
                success.value = true
            } else {
                success.value = false
                password.set("")
                errorMessage.value = R.string.error_passwor_not_valid
            }
        } else {
            onPinError()
        }
    }

    fun login(username: String, password: String) {
        showDialog.value = true

        disposable.add(loginInteractor.login(username, password)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferencesManager.saveUserID(username)
                    preferencesManager.savePassword(password)
                    success.value = true
                    showDialog.value = false
                }, {
                    onLoginError()
                }))
    }

    private fun onPinError() {
        success.value = false
        password.set("")
        errorMessage.value = R.string.error_password_empty
    }

    private fun onLoginError() {
        success.value = false
        password.set("")
        errorMessage.value = R.string.error_login_error
        showDialog.value = false
    }
}