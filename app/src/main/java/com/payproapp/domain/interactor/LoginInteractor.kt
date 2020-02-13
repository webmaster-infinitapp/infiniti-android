package com.payproapp.domain.interactor

import android.util.Log
import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.networkmodel.LoginBody
import com.payproapp.network.ApiService
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginInteractor @Inject constructor(
        private val apiService: ApiService, executor: Scheduler) : BaseInteractor(executor) {

    fun login(username: String, password: String): Flowable<ResponseBody> {
        Log.e("c[_]", "LoginBody(username, PreferencesManager.encodeString(password)): ${LoginBody(username,
                                                                                                   PreferencesManager.encodeString(
                                                                                                           password))}")
        return apiService.login(LoginBody(username, PreferencesManager.encodeString(password)))
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
