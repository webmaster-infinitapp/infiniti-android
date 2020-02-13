package com.payproapp.domain.interactor

import com.payproapp.domain.interactor.base.BaseInteractor
import com.payproapp.model.Balance
import com.payproapp.model.Contact
import com.payproapp.model.Transaction
import com.payproapp.model.networkmodel.PasswordBody
import com.payproapp.model.networkmodel.ProfileResponse
import com.payproapp.network.ApiService
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class HomeInteractor @Inject constructor(
        private val apiService: ApiService, executor: Scheduler) : BaseInteractor(executor) {

    fun getBalances(): Flowable<MutableList<Balance>> = apiService.getBalances()
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun getTransactions(account: String): Flowable<MutableList<Transaction>> = apiService.getTransactions(account)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun getContacts(contacts: MutableList<String>): Flowable<MutableList<Contact>> = apiService.getContacts(contacts)
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun getPublicKey(): Flowable<String> = apiService.getPublicKey()
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun getPrivateKey(password: String): Flowable<Response<ResponseBody>> = apiService.getPrivateKey(
            PasswordBody(PreferencesManager.encodeString(password)))
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())

    fun getProfile(): Flowable<ProfileResponse> = apiService.getProfile()
            .subscribeOn(executor)
            .observeOn(AndroidSchedulers.mainThread())
}