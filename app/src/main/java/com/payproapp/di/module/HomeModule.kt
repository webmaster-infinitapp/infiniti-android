package com.payproapp.di.module

import com.payproapp.ui.home.account.AccountFragment
import com.payproapp.ui.home.account.balances.BalancesFragment
import com.payproapp.ui.home.account.transactions.TransactionsFragment
import com.payproapp.ui.home.receive.ReceiveFragment
import com.payproapp.ui.home.send.SelectContactFragment
import com.payproapp.ui.home.send.SendAmountFragment
import com.payproapp.ui.home.send.SendConfirnTransFragment
import com.payproapp.ui.home.send.SendManuallyFragment
import com.payproapp.ui.home.send.SendMessageFragment
import com.payproapp.ui.home.support.SupportFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun contributeBalanceFragment(): BalancesFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionsFragment(): TransactionsFragment

    @ContributesAndroidInjector
    abstract fun contributeReceiveFragment(): ReceiveFragment

    @ContributesAndroidInjector
    abstract fun contributeSupportFragment(): SupportFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectContactFragment(): SelectContactFragment

    @ContributesAndroidInjector
    abstract fun contributeSendManuallyFragment(): SendManuallyFragment

    @ContributesAndroidInjector
    abstract fun contributeSendAmountFragment(): SendAmountFragment

    @ContributesAndroidInjector
    abstract fun contributeSendMessageFragment(): SendMessageFragment

    @ContributesAndroidInjector
    abstract fun contributeSendConfirmTransFragment(): SendConfirnTransFragment
}