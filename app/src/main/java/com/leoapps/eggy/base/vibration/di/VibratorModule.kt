package com.leoapps.waterapp.common.vibrator.di

import com.leoapps.waterapp.common.vibrator.data.vibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VibratorModule {
    @Binds
    fun bindsVibrator(impl: vibrationManager): VibrationManager
}