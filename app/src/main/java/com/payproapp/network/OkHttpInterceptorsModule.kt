package com.payproapp.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class OkHttpInterceptorsModule {
    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(httpLoggingInterceptor: HttpLoggingInterceptor,
                                  sessionInterceptor: SessionInterceptor,
                                  errorInterceptor: ErrorInterceptor): Array<Interceptor> {
        return arrayOf(httpLoggingInterceptor, sessionInterceptor, errorInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(stethoInterceptor: StethoInterceptor): Array<Interceptor> {
        return arrayOf(stethoInterceptor)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()
}