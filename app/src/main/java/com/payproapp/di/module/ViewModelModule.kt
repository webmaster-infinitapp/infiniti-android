package com.payproapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.payproapp.di.AppViewModelFactory
import com.payproapp.di.ViewModelKey
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.ui.home.account.balances.AddNewTokenViewModel
import com.payproapp.ui.home.send.SendProcessTransactionViewModel
import com.payproapp.ui.home.settings.change_password.ChangePasswordViewModel
import com.payproapp.ui.home.settings.gas_limit.GasLimitViewModel
import com.payproapp.ui.home.settings.gas_price.GasPriceViewModel
import com.payproapp.ui.login.LoginViewModel
import com.payproapp.ui.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GasPriceViewModel::class)
    abstract fun bindGasPriceViewModel(gasPriceViewModel: GasPriceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GasLimitViewModel::class)
    abstract fun bindGasLimitViewModel(gasLimitViewModel: GasLimitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNewTokenViewModel::class)
    abstract fun bindAddNewTokenViewModel(addNewTokenViewModel: AddNewTokenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(changePasswordViewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SendProcessTransactionViewModel::class)
    abstract fun bindSendProcessTransactionViewModel(sendProcessTransactionViewModel: SendProcessTransactionViewModel): ViewModel
}