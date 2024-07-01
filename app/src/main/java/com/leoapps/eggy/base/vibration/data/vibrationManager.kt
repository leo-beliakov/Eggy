package com.leoapps.waterapp.common.vibrator.data

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class vibrationManager @Inject constructor(
    @ApplicationContext val context: Context
) : VibrationManager {

    var vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun vibrateOnClick() {
        vibrator.vibrate(
            VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
        )
    }

    override fun vibratePattern(pattern: LongArray) {
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                pattern,
                -1 // means no repeat
            )
        )
    }
}