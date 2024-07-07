package com.leoapps.waterapp.common.vibrator.di

import com.leoapps.waterapp.common.vibrator.data.VibrationManagerImpl
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VibrationDiModule {

    @Binds
    fun bindsVibrationManager(impl: VibrationManagerImpl): VibrationManager
}