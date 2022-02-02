package com.fefuproject.druzhbank.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import model.interfaces.IPreferenceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(@ApplicationContext val context: Context) :
    IPreferenceProvider {
    private val sharedPreferences =
        context.getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
    var lastVerifiedTimestamp: Long = sharedPreferences.getLong("lastVerifyTS", 0)
    var biometricPreference: Int = sharedPreferences.getInt("biometricPref", STATUS_BIOMETRICS_NONE)
    var privateKey: String? = sharedPreferences.getString("privateKey", null)
    override var token: String? = sharedPreferences.getString("token", null)
    var deviceToken: String? = sharedPreferences.getString("deviceToken", null)
    var lastSentDeviceTokenString: String? =
        sharedPreferences.getString("deviceTokenStr", null)//concat of both tokens

    fun updatePrivateKey(newKey: String) {
        privateKey = newKey
        sharedPreferences.edit().putString("privateKey", privateKey).apply()
    }

    override fun updateToken(newToken: String?) {
        token = newToken
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun updateBiometricPreference(newStatus: Int) {
        biometricPreference = newStatus
        sharedPreferences.edit().putInt("biometricPref", newStatus).apply()
    }

    fun updateLastVerifiedTime() {
        lastVerifiedTimestamp = System.currentTimeMillis()
        sharedPreferences.edit().putLong("lastVerifyTS", lastVerifiedTimestamp).apply()
    }

    fun updateDeviceToken(p0: String) {
        deviceToken = p0
        sharedPreferences.edit().putString("deviceToken", deviceToken).apply()
    }

    fun sentDeviceToken() {
        sharedPreferences.edit().putString("deviceTokenStr", deviceToken + token!!).apply()
    }

    companion object {
        const val STATUS_BIOMETRICS_NONE = 0
        const val STATUS_BIOMETRICS_DISABLED = 1
        const val STATUS_BIOMETRICS_ENABLED = 2
    }
}