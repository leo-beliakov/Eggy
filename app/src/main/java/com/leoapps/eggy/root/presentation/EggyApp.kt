package com.leoapps.eggy.root.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EggyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //todo extract to a different class
        val notificationChanel = NotificationChannel(
            "eggy_channel_id",
            "Timer Alerts",
            NotificationManager.IMPORTANCE_LOW
        )

        notificationChanel.description = "Get timely alerts to perfectly boil your eggs every time."
        notificationChanel.setShowBadge(false)

        val notificationManager = getSystemService<NotificationManager>()
        notificationManager?.createNotificationChannel(notificationChanel)
    }
}