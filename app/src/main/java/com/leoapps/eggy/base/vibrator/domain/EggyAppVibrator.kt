package com.leoapps.waterapp.common.vibrator.domain

interface EggyAppVibrator {
    fun vibrateOnClick()
    fun vibratePattern(pattern: LongArray)
}