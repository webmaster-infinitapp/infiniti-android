package com.payproapp.ui.home.account.balances

import androidx.databinding.ObservableField
import com.payproapp.R
import com.payproapp.domain.interactor.AddNewTokenInteractor
import com.payproapp.model.networkmodel.AddTokenBody
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AddNewTokenViewModel @Inject constructor(
        private val preferencesManager: PreferencesManager,
        private val addNewTokenInteractor: AddNewTokenInteractor,
        private val executor: Scheduler
) : BaseViewModel() {

    val contractAddress: ObservableField<String> = ObservableField("")
    val symbol: ObservableField<String> = ObservableField("")
    val name: ObservableField<String> = ObservableField("")
    val value: ObservableField<String> = ObservableField("")

    fun addNewToken(addTokenBody: AddTokenBody) {
        showDialog.value = true
        disposable.add(addNewTokenInteractor.addNewToken(addTokenBody)
                               .subscribeOn(executor)
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe({
                                              success.value = true
                                              showDialog.value = false
                                          }, {
                                              errorMessage.value = R.string.error_wrong_request
                                              showDialog.value = false
                                          }))
    }

    fun checkTokenData() {
        val contractAdress = contractAddress.get()
        val symbol = symbol.get()
        val value = value.get()
        val name = name.get()

        if (contractAdress.isNullOrEmpty()) {
            errorMessage.value = R.string.error_contract_empty
            return
        }

        if (name.isNullOrEmpty()) {
            errorMessage.value = R.string.error_name_empty
            return
        }

        if (symbol.isNullOrEmpty()) {
            errorMessage.value = R.string.error_token_symbol
            return

        }
        if (value.isNullOrEmpty()) {
            errorMessage.value = R.string.error_value_empty
            return
        }

        preferencesManager.getPasswordPin()?.let { addNewToken(AddTokenBody(contractAdress, symbol, value, it, name)) }
    }
}