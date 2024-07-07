package com.leoapps.progress.di

import com.leoapps.base.notification.platform.NotificationChannelManager
import com.leoapps.progress.platform.notification.BoilProgressNotificationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface ProgressDiModule {

    @Binds
    @IntoSet
    fun bindProgressNotificationManager(
        impl: BoilProgressNotificationManager
    ): NotificationChannelManager
}