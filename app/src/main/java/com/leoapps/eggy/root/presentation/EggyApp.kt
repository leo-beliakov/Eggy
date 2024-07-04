package com.leoapps.eggy.root.presentation

import android.app.Application
import com.leoapps.eggy.base.notification.platform.NotificationChannelManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EggyApp : Application() {

    @Inject
    lateinit var notificationChannelManagers: Set<@JvmSuppressWildcards NotificationChannelManager>

    override fun onCreate() {
        super.onCreate()
        notificationChannelManagers.forEach { it.createChannels() }
    }
}