package com.payproapp.ui.home.settings.gas_price

import androidx.databinding.ObservableField
import com.payproapp.domain.interactor.GasInteractor
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import com.payproapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GasPriceViewModel @Inject constructor(
        private val preferencesManager: PreferencesManager,
        private val gasInteractor: GasInteractor,
        private val executor: Scheduler
) : BaseViewModel() {

    val gasPrice: ObservableField<Int> = ObservableField(preferencesManager.getGasPrice() / 1000)

    fun updateGasPrice() {
        val gasPriceValue = gasPrice.get()!! * 1000

        showDialog.value = true
        disposable.add(gasInteractor.updateGasPrice(gasPriceValue)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferencesManager.saveGasPrice(gasPriceValue)
                    showDialog.value = false
                    success.value = true
                }, {
                    errorMessage.value = R.string.error_login_error
                    showDialog.value = false
                }))
    }
}