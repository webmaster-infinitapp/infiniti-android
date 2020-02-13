package com.payproapp.ui.home.send.process_transaction

import com.payproapp.domain.interactor.SendTokenInteractor
import com.payproapp.model.networkmodel.OnSendBody
import com.payproapp.model.networkmodel.TransactionBody
import com.payproapp.model.networkmodel.TransferTokenBody
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProcessTransactionViewModel @Inject constructor(
        private val preferencesManager: PreferencesManager,
        private val sendTokenInteractor: SendTokenInteractor,
        private val executor: Scheduler
) : BaseViewModel() {

    private fun transferToken(transferTokenBody: TransferTokenBody) {
        disposable.add(sendTokenInteractor.transferToken(transferTokenBody)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                }))
    }

    private fun transferTokenEther(transactionBody: TransactionBody) {
        disposable.add(sendTokenInteractor.transferTokenEther(transactionBody)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                }))
    }

    fun sendToken(sendBody: OnSendBody) {
        if (sendBody.tokenSymbol == "ETH") {
            preferencesManager.getPasswordPin()?.let {
                transferTokenEther(TransactionBody(sendBody.description, sendBody.amount, sendBody.destinationKey, PreferencesManager.encodeString(it)))
            }
        } else {
            preferencesManager.getPublicKey()?.let { publicKey ->
                preferencesManager.getPasswordPin()?.let { password ->
                    transferToken(
                            TransferTokenBody(sendBody.description, sendBody.amount, sendBody.destinationKey, PreferencesManager.encodeString(password),
                                    "0x811C92D5186796d8505a9046C67F3386516949f7"))
                }
            }
        }
    }
}