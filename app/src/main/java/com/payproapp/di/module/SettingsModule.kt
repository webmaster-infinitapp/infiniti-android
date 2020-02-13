package com.payproapp.di.module

import com.payproapp.ui.home.settings.SettingsFragment
import com.payproapp.ui.home.settings.WebViewFragment
import com.payproapp.ui.home.settings.change_password.ChangePasswordFragment
import com.payproapp.ui.home.settings.private_key.PrivateKeyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsModule {
    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributePrivateKeyFragment(): PrivateKeyFragment

    @ContributesAndroidInjector
    abstract fun contributeWebViewFragment(): WebViewFragment
}