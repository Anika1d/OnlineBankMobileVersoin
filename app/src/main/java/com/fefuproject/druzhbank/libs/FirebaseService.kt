package com.fefuproject.druzhbank.libs

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FirebaseService : FirebaseMessagingService() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val builder = NotificationCompat.Builder(this, "operations")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body).setAutoCancel(true)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "operations",
            "Операции",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)
        manager.notify(0, builder.build())
    }

    override fun onNewToken(p0: String) =
        preferenceProvider.updateDeviceToken(p0)
}