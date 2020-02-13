package com.payproapp.di.module

import com.payproapp.BuildConfig
import com.payproapp.domain.executor.JobExecutor
import com.payproapp.environment.EnvironmentModule
import com.payproapp.network.ApiService
import com.payproapp.network.ErrorInterceptor
import com.payproapp.network.OkHttpInterceptorsModule
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpInterceptorsModule::class, EnvironmentModule::class, ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideThreadExecutor(): Scheduler {
        return Schedulers.from(JobExecutor())
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
    }
}