package com.payproapp.util.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {
    @Provides
    @Singleton
    fun provideDefaultGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

        return gsonBuilder
    }

    @Provides
    @Singleton
    fun provideGson(gsonBuilder: GsonBuilder): Gson {
        return gsonBuilder.create()
    }
}