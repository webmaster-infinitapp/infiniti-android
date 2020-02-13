package com.payproapp.ui.home

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.payproapp.domain.interactor.HomeInteractor
import com.payproapp.domain.interactor.SendTokenInteractor
import com.payproapp.model.Balance
import com.payproapp.model.Contact
import com.payproapp.model.Resource
import com.payproapp.model.Transaction
import com.payproapp.model.networkmodel.OnSendBody
import com.payproapp.model.networkmodel.ProfileResponse
import com.payproapp.model.state.SendState
import com.payproapp.ui.base.BaseViewModel
import com.payproapp.util.preferences.PreferencesManager
import io.intercom.android.sdk.Intercom
import io.intercom.android.sdk.identity.Registration
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.doAsyncResult
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val homeInteractor: HomeInteractor,
        private val sendTokenInteractor: SendTokenInteractor,
        private val executor: Scheduler,
        private val preferencesManager: PreferencesManager
) : BaseViewModel() {

    val qrCode: MutableLiveData<Bitmap> = MutableLiveData()

    val contactList: MutableLiveData<MutableList<Contact>> = MutableLiveData()
    val balances: MutableLiveData<MutableList<Balance>> = MutableLiveData()
    val transactions: MutableLiveData<MutableList<Transaction>> = MutableLiveData()
    val publicKey: MutableLiveData<String> = MutableLiveData()
    val privateKey: MutableLiveData<String> = MutableLiveData()
    val profileInfo: MutableLiveData<ProfileResponse> = MutableLiveData()

    val finalContactList: MutableLiveData<MutableList<Contact>> = MutableLiveData()

    val stateOnSend: PublishSubject<Resource<OnSendBody>?> = PublishSubject.create()

    lateinit var sendBody: OnSendBody

    fun sendSelectContactsState() {
        sendBody = OnSendBody(SendState.SELECT_CONTACT)
        onNext()
    }

    fun sendManuallyState() {
        sendBody.sendState = SendState.SET_ADDRESS
        onNext()
    }

    fun sendAmountState() {
        sendBody.sendState = SendState.AMOUNT_ASSET
        onNext()
    }

    fun sendMessageState() {
        sendBody.sendState = SendState.MESSAGE
        onNext()
    }

    fun confirmTransactionState() {
        sendBody.sendState = SendState.CONFIRM
        onNext()
    }

    fun processTransactionState() {
        sendBody.sendState = SendState.PROCESSING
        onNext()
    }

    private fun onNext() {
        stateOnSend.onNext(Resource.success(sendBody))
    }

    fun launchIntercomChat() {
        val userID = preferencesManager.getUserID()
        userID?.let {
            Intercom.client().registerIdentifiedUser(Registration().withUserId(it))
            Intercom.client().displayMessenger()
        }
    }

    fun retrieveBalances() {
        showProgressBar.set(true)
        disposable.add(homeInteractor.getBalances()
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        showProgressBar.set(false)
                        balances.value = it
                        preferencesManager.savePublicKey(it[0].account)
                        saveTokensFromBalances(it)
                    }
                }, {
                    showProgressBar.set(false)
                    Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                }))
    }

    private fun saveTokensFromBalances(balances: MutableList<Balance>) {
        preferencesManager.saveTokens(balances)
    }

    fun retrieveTransactions(account: String) {
        showProgressBar.set(true)

        disposable.add(homeInteractor.getTransactions(account)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        transactions.value = it
                    }
                }, {
                    Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                }))
    }

    fun retrieveContacts(contacts: MutableList<String>) {
        disposable.add(homeInteractor.getContacts(contacts)
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        finalContactList.value = it
                    }
                }, {

                }))
    }

    private fun retrievePublicKey() {
        disposable.add(homeInteractor.getPublicKey()
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        publicKey.value = it
                        preferencesManager.savePublicKey(it)
                    }
                }, {
                    Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                }))
    }

    fun retrievePrivateKey() {
        showProgressBar.set(true)
        disposable.add(homeInteractor.getPrivateKey(preferencesManager.getPasswordPin() ?: "")
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    privateKey.value = it.body()?.string()
                    showProgressBar.set(false)
                }, {
                    Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                    showProgressBar.set(false)
                }))
    }

    fun retrieveUserProfile() {
        showProgressBar.set(true)
        disposable.add(homeInteractor.getProfile()
                .subscribeOn(executor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferencesManager.saveGasLimit(it.gasLimit.toInt())
                    preferencesManager.saveGasPrice(it.gasPrice.toInt())

                    profileInfo.value = it
                    showProgressBar.set(false)
                }, {
                    Timber.e(it, "Hemos tenido un error %s", it.localizedMessage)
                    showProgressBar.set(false)
                }))
    }

    fun checkPublicKey() {
        if (preferencesManager.getPublicKey().isNullOrEmpty()) {
            retrievePublicKey()
        } else {
            publicKey.value = preferencesManager.getPublicKey()
        }
    }

    fun generateQR(content: String, width: Int, height: Int, colorRes1: Int?, colorRes2: Int?) {
        val qrBitmap = doAsyncResult {
            encodeQR(content, width, height, colorRes1, colorRes2)
        }

        qrCode.value = qrBitmap.get()
    }

    fun retrievePhoneContacts(contentResolver: ContentResolver) {

        val columnName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val columnPhone = ContactsContract.CommonDataKinds.Phone.NUMBER

        val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                "$columnName ASC"
        )

        cursor?.let {
            if (it.count > 0) {
                val contacts: MutableList<Contact> = arrayListOf()

                while (cursor.moveToNext()) {

                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val hasPhoneNumber = Integer.parseInt(
                            cursor.getString(
                                    cursor.getColumnIndex(
                                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                                    )
                            )
                    )

                    if (hasPhoneNumber > 0) {
                        val phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id),
                                null
                        )

                        phoneCursor?.let {
                            if (phoneCursor.moveToNext()) {
                                val name = cursor.getString(cursor.getColumnIndex(columnName))
                                val phoneNumber =
                                        phoneCursor.getString(phoneCursor.getColumnIndex(columnPhone))

                                //TODO CALL SERVER

                                contacts.add(Contact(name, phoneNumber, ""))
                            }
                            phoneCursor.close()
                        }
                    }
                }
                contactList.value = contacts
            }
            cursor.close()
        }
    }

    private fun encodeQR(
            content: String,
            width: Int,
            height: Int,
            colorRes1: Int?,
            colorRes2: Int?
    ): Bitmap {
        var color1 = Color.BLACK
        var color2 = Color.WHITE

        colorRes1?.let {
            color1 = it
        }

        colorRes2?.let {
            color2 = it
        }

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) color1 else color2)
            }
        }

        return bmp
    }
}