package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.networkmodel.AddTokenBody
import com.payproapp.network.ApiService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class AddNewTokenInteractor @Inject constructor(private val apiService: ApiService,
                                                executor: Scheduler) : BaseInteractor(executor) {
    fun addNewToken(addTokenBody: AddTokenBody): Flowable<ResponseBody> = apiService.addNewToken(addTokenBody)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}