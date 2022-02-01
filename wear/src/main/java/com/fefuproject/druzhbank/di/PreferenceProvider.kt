package com.fefuproject.druzhbank.di

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import model.interfaces.IPreferenceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(@ApplicationContext val context: Context) : IPreferenceProvider{
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var lastVerifiedTimestamp: Long = sharedPreferences.getLong("lastVerifyTS", 0)
    override var token: String? = sharedPreferences.getString("token", null)
    var PIN: String? = sharedPreferences.getString("PIN", null)
    var currencyEnabled: Boolean = sharedPreferences.getBoolean("cur_enabled", true)

    override fun updateToken(newToken: String?) {
        token = newToken
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun updatePIN(newPIN: String?){
        PIN = newPIN
        sharedPreferences.edit().putString("PIN", newPIN).apply()
    }

    fun updateLastVerifiedTime() {
        lastVerifiedTimestamp = System.currentTimeMillis()
        sharedPreferences.edit().putLong("lastVerifyTS", lastVerifiedTimestamp).apply()
    }

    fun updateCurrency(newState: Boolean) {
        currencyEnabled = newState
        sharedPreferences.edit().putBoolean("cur_enabled", newState).apply()
    }
}