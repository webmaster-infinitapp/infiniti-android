package com.payproapp.di.module

import com.payproapp.ui.home.HomeActivity
import com.payproapp.ui.home.account.balances.AddNewTokenActivity
import com.payproapp.ui.home.account.transactions.TransactionDetailsActivity
import com.payproapp.ui.home.send.SendFinishedTransactionActivity
import com.payproapp.ui.home.send.SendProcessTransactionActivity
import com.payproapp.ui.home.settings.change_password.ChangePasswordActivity
import com.payproapp.ui.home.settings.gas_limit.GasLimitActivity
import com.payproapp.ui.home.settings.gas_price.GasPriceActivity
import com.payproapp.ui.login.LoginActivity
import com.payproapp.ui.signup.SignUpActivity
import com.payproapp.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SignUpModule::class])
    abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector(modules = [HomeModule::class, SettingsModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeAddNewTokenActivity(): AddNewTokenActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeTransactionDetailsActivity(): TransactionDetailsActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeGasPriceActivity(): GasPriceActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeGasLimitActivity(): GasLimitActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeChangePasswordActivity(): ChangePasswordActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeSendProcessTransactionActivity(): SendProcessTransactionActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeSendFinishedTransactionActivity(): SendFinishedTransactionActivity
}