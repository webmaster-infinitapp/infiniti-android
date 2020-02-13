package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.networkmodel.ChangePasswordBody
import com.payproapp.network.ApiService
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class ChangePasswordInteractor @Inject constructor(private val apiService: ApiService,
                                                   executor: Scheduler) : BaseInteractor(executor) {
    fun changePassword(currentPassword: String, newPassword: String):
            Flowable<ResponseBody> = apiService.changePassword(
            ChangePasswordBody(PreferencesManager.encodeString(currentPassword),
                               PreferencesManager.encodeString(newPassword)))
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}