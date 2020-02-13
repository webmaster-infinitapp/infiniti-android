package com.payproapp.network

import android.app.Application
import com.google.gson.Gson
import com.payproapp.model.networkmodel.ErrorBody
import com.payproapp.ui.signup.SignUpActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Error interceptor to intercept when servers return a token expired exception and
 * send the user to the login screen
 */

@Singleton
class ErrorInterceptor @Inject constructor(val application: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        request = requestBuilder.build()

        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            when (response.code()) {
                500 -> {
                    response.body()?.let {
                        if (it.string().contains("expired", true))
                            SignUpActivity.start(application.applicationContext)
                    }
                }
            }

            response.body()?.let {
                val gson = Gson()
                val error = gson.fromJson(it.string(), ErrorBody::class.java)

                val contentType = it.contentType()
                val body = ResponseBody.create(contentType, gson.toJson(error.message))
                return response.newBuilder().body(body).build()
            }
        }
        return response
    }
}