package com.leoapps.waterapp.common.vibrator.di

import com.leoapps.waterapp.common.vibrator.data.VibratorImpl
import com.leoapps.waterapp.common.vibrator.domain.EggyAppVibrator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VibratorModule {
    @Binds
    fun bindsVibrator(impl: VibratorImpl): EggyAppVibrator
}