package com.payproapp.ui.home.settings.gas_limit

import androidx.databinding.ObservableField
import com.payproapp.R
import com.payproapp.domain.interactor.GasInteractor
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GasLimitViewModel @Inject constructor(
        private val preferencesManager: PreferencesManager,
        private val gasInteractor: GasInteractor,
        private val executor: Scheduler
) : BaseViewModel() {

    val gasLimit: ObservableField<String> = ObservableField(preferencesManager.getGasLimit()?.toString()
                                                                    ?: "")

    fun updateGasLimit(gasLimit: Int) {
        showDialog.value = true
        disposable.add(gasInteractor.updateGasLimit(gasLimit)
                               .subscribeOn(executor)
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe({
                                              preferencesManager.saveGasLimit(gasLimit)
                                              success.value = true
                                              showDialog.value = false
                                          }, {
                                              showDialog.value = false
                                              errorMessage.value = R.string.error_login_error
                                          }))
    }

    fun checkGasLimit() {
        val gasLimit = gasLimit.get()

        if (gasLimit.isNullOrEmpty()) {
            errorMessage.value = R.string.error_gas_limit_empty
            return
        }

        updateGasLimit(gasLimit.toInt())
    }
}