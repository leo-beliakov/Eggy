package com.leoapps.eggy.base.vibration.presentation

import androidx.compose.runtime.compositionLocalOf
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager


val LocalVibrationManager = compositionLocalOf<VibrationManager> {
    error("No WaterAppVibrator provided")
}