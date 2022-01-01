package com.fefuproject.weardruzhbank.Model

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(@ApplicationContext val context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var lastVerifiedTimestamp: Long = sharedPreferences.getLong("lastVerifyTS", 0)
    var token: String? = "asdf" //sharedPreferences.getString("token", null) TODO:TEMP

    fun updateLastVerifiedTime() {
        lastVerifiedTimestamp = System.currentTimeMillis()
        sharedPreferences.edit().putLong("lastVerifyTS", lastVerifiedTimestamp).apply()
    }
}