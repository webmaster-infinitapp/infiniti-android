package com.payproapp


import android.app.Activity
import android.app.Application
import com.payproapp.di.component.AppComponent
import com.payproapp.environment.EnvironmentConfiguration
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.intercom.android.sdk.Intercom
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var environmentConfiguration: EnvironmentConfiguration


    override fun onCreate() {
        super.onCreate()
        AppComponent.Initializer.init(this)
        environmentConfiguration.configure()
        Intercom.initialize(this, "android_sdk-33727861a67ba62364b150d95d3d8812f5e3584a", "u32hbwiy")
        Timber.d("${BuildConfig.APPLICATION_ID} start")
    }


    override fun activityInjector() = dispatchingActivityInjector
}