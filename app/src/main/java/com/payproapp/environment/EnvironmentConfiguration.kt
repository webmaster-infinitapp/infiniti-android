package com.payproapp.environment

import android.app.Application
import com.facebook.stetho.Stetho
import com.payproapp.BuildConfig
import com.payproapp.util.logging.CrashReportingTree
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EnvironmentConfiguration @Inject constructor(private val app: Application) {

    fun configure() {
        //Fabric.with(app, Crashlytics())
        Schedulers.io().createWorker().schedule { Stetho.initializeWithDefaults(app) }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}