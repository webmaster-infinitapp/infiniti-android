package com.payproapp.util.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import com.payproapp.model.Balance
import java.nio.charset.Charset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(app: Application) {

    private val prefsName = "PayProPreferences"
    private val userID = "user_id"
    private val passwordPin = "password_pin"
    private val publicKey = "public_key"
    private val tokenList = "tokens"
    private val GAS_LIMIT_KEY = "gas_limit"
    private val GAS_PRICE_KEY = "gas_price"

    private val sharedPreferences: SharedPreferences
    private val gson: Gson = Gson()

    init {
        sharedPreferences = app.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    fun saveUserID(userName: String) {
        sharedPreferences.edit().putString(userID, userName).apply()
    }

    fun getUserID(): String? {
        return sharedPreferences.getString(userID, "")
    }

    fun savePassword(pin: String) {
        sharedPreferences.edit().putString(passwordPin, encodeString(pin)).apply()
    }

    fun getPasswordPin(): String? {
        sharedPreferences.getString(passwordPin, "")?.let {
            return decodeString(it)
        }
    }

    fun savePublicKey(account: String) {
        sharedPreferences.edit().putString(publicKey, account).apply()
    }

    fun getPublicKey(): String? {
        return sharedPreferences.getString(publicKey, "")
    }

    fun saveGasLimit(gasLimit: Int) {
        sharedPreferences.edit().putInt(GAS_LIMIT_KEY, gasLimit).apply()
    }

    fun getGasLimit(): Int? {
        return sharedPreferences.getInt(GAS_LIMIT_KEY, 0)
    }

    fun saveGasPrice(gasPrice: Int) {
        sharedPreferences.edit().putInt(GAS_PRICE_KEY, gasPrice).apply()
    }

    fun getGasPrice(): Int {
        return sharedPreferences.getInt(GAS_PRICE_KEY, 0)
    }

    fun saveTokens(tokens: MutableList<Balance>) {
        val gson = Gson()

        sharedPreferences.edit().putString(tokenList, gson.toJson(tokens)).apply()
    }

    fun getTokens(): MutableList<Balance>? {
        val gson = Gson()

        return gson.fromJson(sharedPreferences.getString(tokenList, ""), Array<Balance>::class.java).toMutableList()
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        @JvmStatic
        var username = ""

        fun encodeString(text: String): String =
                Base64.encodeToString(text.toByteArray(Charset.defaultCharset()), Base64.DEFAULT).replace("\n", "")


        fun decodeString(encodedText: String): String =
                String(Base64.decode(encodedText, Base64.DEFAULT), Charset.defaultCharset())
    }
}