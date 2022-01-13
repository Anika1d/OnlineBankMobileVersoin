package com.fefuproject.druzhbank.di

import android.content.Context
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.ValuteResponseModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(@ApplicationContext val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
    var lastVerifiedTimestamp: Long = sharedPreferences.getLong("lastVerifyTS", 0)
    var biometricPreference: Int = sharedPreferences.getInt("biometricPref", STATUS_BIOMETRICS_NONE)
    var privateKey: String? = sharedPreferences.getString("privateKey", null)
    var token: String? = sharedPreferences.getString("token", null)
   lateinit var cardList:List<CardModel>

    lateinit var valuteList:List<ValuteResponseModel>
    fun updatePrivateKey(newKey: String) {
        privateKey = newKey
        sharedPreferences.edit().putString("privateKey", privateKey).apply()
    }

    fun updateToken(newToken: String) {
        token = newToken
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun updateBiometricPreference(newStatus: Int) {
        biometricPreference = newStatus
        sharedPreferences.edit().putInt("newStatus", newStatus).apply()
    }

    fun updateLastVerifiedTime() {
        lastVerifiedTimestamp = System.currentTimeMillis()
        sharedPreferences.edit().putLong("lastVerifyTS", lastVerifiedTimestamp).apply()
    }

    companion object {
        const val STATUS_BIOMETRICS_NONE = 0
        const val STATUS_BIOMETRICS_DISABLED = 1
        const val STATUS_BIOMETRICS_ENABLED = 2
    }
}