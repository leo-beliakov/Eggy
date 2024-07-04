package com.leoapps.eggy.root.presentation

import android.app.Application
import com.leoapps.eggy.progress.platform.notification.BoilProgressNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EggyApp : Application() {

    @Inject
    lateinit var notificationManager: BoilProgressNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.createChannels()
    }
}