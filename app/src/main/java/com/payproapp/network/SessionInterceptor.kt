package com.payproapp.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Session interceptor is an Interceptor to create the correct request for the server
 * Intercepts the request to get the Authorization header and saves into it self to use the
 * token in the next request
 */

@Singleton
class SessionInterceptor @Inject constructor() : Interceptor {
    private var authorizationToken: String? = null
    private var sessionIdCookie: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        //get request
        var request = chain.request()
        //check if token is not empty and add to request
        val requestBuilder = request.newBuilder()

        authorizationToken?.let { token ->
            Timber.d("creating authorization header with %s", token)
            requestBuilder.addHeader("Authorization", token)
        }

        sessionIdCookie?.let { cookie ->
            Timber.d("creating cookie with %s", cookie)
            requestBuilder.addHeader("Cookie", cookie)
        }

        request = requestBuilder.build()

        //proceed with the request
        val response = chain.proceed(request)
        val authorization = response.header("Authorization")?.removePrefix("Paypro ")
        val cookie = response.header("Set-Cookie")
        Timber.d("Cookie found: %s", sessionIdCookie)
        Timber.d("Authorization found: %s", authorization)
        authorization?.let {
            authorizationToken = authorization
        }
        cookie?.let {
            sessionIdCookie = cookie
        }
        return response
    }
}