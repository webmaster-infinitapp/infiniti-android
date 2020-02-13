package com.payproapp.di.module

import com.payproapp.ui.login.LoginUsernameFragment
import com.payproapp.ui.signup.register.CreatePasswordFragment
import com.payproapp.ui.signup.register.SignUpFragment
import com.payproapp.ui.signup.register.SmsValidationFragment
import com.payproapp.ui.signup.register.UsernameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignUpModule {

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun contributeSmsValidationFragment(): SmsValidationFragment

    @ContributesAndroidInjector
    abstract fun contributeCreatePasswordFragment(): CreatePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginUsernameFragment(): LoginUsernameFragment

    @ContributesAndroidInjector
    abstract fun contributeUsernameFragment(): UsernameFragment
}