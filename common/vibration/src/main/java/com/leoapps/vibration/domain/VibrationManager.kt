package com.leoapps.waterapp.common.vibrator.domain

interface VibrationManager {
    fun vibrateOnClick()
    fun vibratePattern(pattern: LongArray)
}