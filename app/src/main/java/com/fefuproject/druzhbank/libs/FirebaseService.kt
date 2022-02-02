package com.fefuproject.druzhbank.libs

import com.fefuproject.druzhbank.di.PreferenceProvider
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseService : FirebaseMessagingService() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onNewToken(p0: String) =
        preferenceProvider.updateDeviceToken(p0)
}