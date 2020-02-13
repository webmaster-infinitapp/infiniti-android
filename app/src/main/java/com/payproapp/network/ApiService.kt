package com.payproapp.network

import com.payproapp.model.Balance
import com.payproapp.model.Contact
import com.payproapp.model.Transaction
import com.payproapp.model.networkmodel.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(@Body loginRequest: LoginBody): Flowable<ResponseBody>

    @POST("otp")
    fun sendOtp(@Body otpRequest: OtpBody): Flowable<OtpResponse>

    @GET("otp")
    fun verifyOtp(@Query("otp") otp: String): Flowable<OtpResponse>

    @POST("registro")
    fun register(@Body registerRequest: RegisterBody): Flowable<OtpResponse>

    @POST("checkUserId")
    fun checkuserId(@Query("idUsuario") userId: String): Flowable<OtpResponse>

    @GET("balance")
    fun getBalances(): Flowable<MutableList<Balance>>

    @GET("transacHist")
    fun getTransactions(@Query("cuenta") account: String): Flowable<MutableList<Transaction>>

    @POST("updateGasPrice")
    fun updateGasPrice(@Query("newGasPrice") gasPrice: Int): Flowable<ResponseBody>

    @POST("updateGasLimit")
    fun updateGasLimit(@Query("newGasLimit") gasLimit: Int): Flowable<ResponseBody>

    @POST("addNewToken")
    fun addNewToken(@Body addToken: AddTokenBody): Flowable<ResponseBody>

    @GET("getPubKey")
    fun getPublicKey(): Flowable<String>

    @POST("getPrivKey")
    fun getPrivateKey(@Body passwordBody: PasswordBody): Flowable<Response<ResponseBody>>

    @POST("changepass")
    fun changePassword(@Body changePassword: ChangePasswordBody): Flowable<ResponseBody>

    @POST("transferToken")
    fun transferToken(@Body transferToeken: TransferTokenBody): Flowable<ResponseBody>

    @POST("transaccion")
    fun transferTokenEther(@Body transactionBody: TransactionBody): Flowable<ResponseBody>

    @GET("profile")
    fun getProfile(): Flowable<ProfileResponse>

    @POST("contactos")
    fun getContacts(@Body transactionBody: MutableList<String>): Flowable<MutableList<Contact>>
}