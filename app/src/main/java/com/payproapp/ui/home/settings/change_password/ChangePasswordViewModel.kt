package com.payproapp.ui.home.settings.change_password

import androidx.databinding.ObservableField
import com.payproapp.domain.interactor.ChangePasswordInteractor
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import com.payproapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
        private val preferencesManager: PreferencesManager,
        private val changePasswordInteractor: ChangePasswordInteractor,
        private val executor: Scheduler
) : BaseViewModel() {

    val password: ObservableField<String> = ObservableField("")
    val infoText: ObservableField<Int> = ObservableField(R.string.message_create_password)

    private var currentPassword: String = ""
    private var newPassword: String = ""

    private fun currentPasswordState() {
        infoText.set(R.string.message_create_password)
        password.set("")
        currentPassword = ""
        newPassword = ""
    }

    private fun newPasswordState() {
        infoText.set(R.string.message_confirm_current_password)
        password.set("")
    }

    fun nextPasswordState() {
        if (!currentPassword.isEmpty()) {
            password.get()?.let {
                newPassword = it

                if (currentPassword == newPassword) {
                    changePassword()
                } else {
                    errorMessage.value = R.string.error_passwor_not_valid
                    currentPasswordState()
                }
            }

        } else {
            password.get()?.let {
                currentPassword = it
                newPasswordState()
            }
        }
    }

    fun changePassword() {
        val password = newPassword

        preferencesManager.getPasswordPin()?.let {
            showDialog.value = true
            disposable.add(changePasswordInteractor.changePassword(it, password)
                    .subscribeOn(executor)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        success.value = true
                        showDialog.value = false
                        preferencesManager.savePassword(password)
                    }, {
                        showDialog.value = false
                        errorMessage.value = R.string.error_password_change_error
                        currentPasswordState()
                    }))
        }
    }
}