package com.payproapp.environment

import android.app.Application
import androidx.annotation.NonNull
import com.payproapp.R
import com.payproapp.network.OkHttpInterceptors
import com.payproapp.network.OkHttpNetworkInterceptors
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
class EnvironmentModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
            app: Application, @OkHttpInterceptors @NonNull interceptors: Array<Interceptor>, @OkHttpNetworkInterceptors @NonNull networkInterceptors: Array<Interceptor>): OkHttpClient {

        // loading CAs from an InputStream
        val cf = CertificateFactory.getInstance("X.509")
        val caInput = app.resources.openRawResource(R.raw.paypro_server_dev)
        val ca: X509Certificate = caInput.use {
            cf.generateCertificate(it) as X509Certificate
        }

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs inputStream our KeyStore
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val context: SSLContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        val trustManager = tmf.trustManagers[0] as X509TrustManager

        val cacheDir = File(app.cacheDir, "http")
        val cache = Cache(cacheDir, 1000000)


        //Create builder
        val builder = OkHttpClient.Builder()


        builder.sslSocketFactory(context.socketFactory, trustManager)

        builder.hostnameVerifier { _, _ ->
            HttpsURLConnection.getDefaultHostnameVerifier().run {
                true
            }
        }

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }

        for (networkInterceptor in networkInterceptors) {
            builder.addNetworkInterceptor(networkInterceptor)
        }

        builder.cache(cache)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }
}