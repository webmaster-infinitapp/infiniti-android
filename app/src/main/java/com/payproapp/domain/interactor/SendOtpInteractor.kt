package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.networkmodel.LoginBody
import com.payproapp.model.networkmodel.OtpBody
import com.payproapp.model.networkmodel.OtpResponse
import com.payproapp.model.networkmodel.RegisterBody
import com.payproapp.network.ApiService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SendOtpInteractor @Inject constructor(
        private val apiService: ApiService, executor: Scheduler) : BaseInteractor(executor) {

    fun invoke(phone: String, prefix: String): Flowable<OtpResponse> = apiService.login(LoginBody.fake())
            .concatMap { apiService.sendOtp(OtpBody(phone, prefix)) }
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun verify(sms: String): Flowable<OtpResponse> = apiService.verifyOtp(sms)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun checkUserId(userId: String): Flowable<OtpResponse> = apiService.checkuserId(userId)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun register(registerBody: RegisterBody): Flowable<OtpResponse> = apiService.register(registerBody)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}