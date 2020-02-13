package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.network.ApiService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class GasInteractor @Inject constructor(
        private val apiService: ApiService, executor: Scheduler) : BaseInteractor(executor) {

    fun updateGasPrice(gasPrice: Int): Flowable<ResponseBody> = apiService.updateGasPrice(gasPrice)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun updateGasLimit(gasLimit: Int): Flowable<ResponseBody> = apiService.updateGasLimit(gasLimit)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}