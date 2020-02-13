package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.networkmodel.TransactionBody
import com.payproapp.model.networkmodel.TransferTokenBody
import com.payproapp.network.ApiService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class SendTokenInteractor @Inject constructor(
        private val apiService: ApiService, executor: Scheduler) : BaseInteractor(executor) {

    fun transferToken(transferTokenBody: TransferTokenBody): Flowable<ResponseBody> = apiService.transferToken(
            transferTokenBody)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun transferTokenEther(transactionBody: TransactionBody): Flowable<ResponseBody> = apiService.transferTokenEther(
            transactionBody)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}