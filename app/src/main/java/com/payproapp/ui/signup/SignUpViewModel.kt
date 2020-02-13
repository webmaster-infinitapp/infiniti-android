package com.payproapp.ui.signup

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.payproapp.R
import com.payproapp.domain.interactor.SendOtpInteractor
import com.payproapp.model.Resource
import com.payproapp.model.networkmodel.OnboardingBody
import com.payproapp.model.networkmodel.RegisterBody
import com.payproapp.model.state.RegisterState
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject


class SignUpViewModel @Inject constructor(
        private val sendOtpInteractor: SendOtpInteractor,
        private val executor: Scheduler,
        private val preferencesManager: PreferencesManager) : BaseViewModel() {

    val smsPass: ObservableField<String> = ObservableField("")
    val password: ObservableField<String> = ObservableField("")
    val username: ObservableField<String> = ObservableField("")
    val infoText: ObservableField<Int> = ObservableField(0)

    val stateOnBoarding: PublishSubject<Resource<OnboardingBody>?> = PublishSubject.create()

    private var firstPass: String = ""
    private var userId: String = ""
    private var name: String = ""
    private var phone: String = ""
    private var prefix: String = ""

    init {
        showDialog.value = false
        infoText.set(R.string.message_create_password)
    }

    fun registerState() {
        stateOnBoarding.onNext(Resource.success(OnboardingBody(RegisterState.REGISTER)))
    }

    fun loginState() {
        stateOnBoarding.onNext(Resource.success(OnboardingBody(RegisterState.LOGIN)))
    }

    fun verifyPassState() {
        infoText.set(R.string.message_confirm_new_password)
        password.set("")
    }

    fun sendOtp(username: String, prefix: String, phone: String) {
        showDialog.value = true
        disposable.add(sendOtpInteractor.invoke(phone, prefix)
                               .subscribeOn(executor)
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe({
                                              showDialog.value = false
                                              this.phone = phone
                                              this.prefix = prefix
                                              this.name = username
                                              stateOnBoarding.onNext(
                                                      Resource.success(OnboardingBody(RegisterState.SMS_CODE)))
                                          }, {
                                              Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                                              showDialog.value = false
                                          })
        )
    }

    fun verifyOtp(smsCode: String) {
        if (smsCode.length == 4) {
            showDialog.value = true
            disposable.add(sendOtpInteractor.verify(smsCode)
                                   .subscribeOn(executor)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe({
                                                  showDialog.value = false
                                                  stateOnBoarding.onNext(
                                                          Resource.success(OnboardingBody(RegisterState.USERNAME)))
                                              }, {
                                                  Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                                                  showDialog.value = false
                                                  smsPass.set("")
                                                  errorMessage.value = R.string.error_sms_code_error
                                              })
            )
        } else {
            showDialog.value = false
            smsPass.set("")
            errorMessage.value = R.string.error_sms_code_error
        }
    }

    fun registerUsername(userId: String) {
        if (TextUtils.isEmpty(userId)) {
            username.set("")
            errorMessage.value = R.string.error_username_empty
        } else {
            showDialog.value = true

            disposable.add(sendOtpInteractor.checkUserId(userId)
                                   .subscribeOn(executor)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe({
                                                  showDialog.value = false
                                                  this.userId = userId
                                                  stateOnBoarding.onNext(
                                                          Resource.success(OnboardingBody(RegisterState.PASSWORD)))

                                              }, {
                                                  Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                                                  showDialog.value = false
                                                  username.set("")
                                                  errorMessage.value = R.string.error_username_exists
                                              }))
        }
    }

    fun loginUsername(userId: String) {
        this.userId = userId
        PreferencesManager.username = userId
        stateOnBoarding.onNext(Resource.success(OnboardingBody(RegisterState.LOGIN_PASSWORD)))
    }

    fun register(passwordEntered: String) {
        if (passwordEntered.isEmpty() || passwordEntered.length < 6) {
            errorMessage.value = R.string.error_password_empty
            return
        }

        if (TextUtils.isEmpty(firstPass)) {
            //create password
            if (passwordEntered.length == 6) {
                firstPass = passwordEntered
                stateOnBoarding.onNext(Resource.success(OnboardingBody(RegisterState.PASSWORD_VERIFIED)))
            }
        } else {
            //confirm password
            if (firstPass == passwordEntered) {
                showDialog.value = true
                disposable.add(sendOtpInteractor.register(
                        RegisterBody(userId, name, prefix, phone, PreferencesManager.encodeString(passwordEntered),
                                     name))
                                       .subscribeOn(executor)
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe({
                                                      preferencesManager.saveUserID(userId)
                                                      showDialog.value = false
                                                      stateOnBoarding.onComplete()
                                                  }, {
                                                      Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                                                      resetPasswords(R.string.error_register)
                                                  }))
            } else {
                //password not match reset
                resetPasswords(R.string.error_passwor_not_valid)
            }
        }

    }

    private fun resetPasswords(message: Int) {
        showDialog.value = false
        errorMessage.value = message //show error message
        infoText.set(R.string.message_create_password) //message info
        firstPass = "" // clear var
        password.set("") // clear text
    }
}