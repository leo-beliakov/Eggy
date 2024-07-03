package com.leoapps.eggy.progress.service

import android.Manifest
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import com.leoapps.eggy.R
import com.leoapps.eggy.base.common.utils.convertMsToText
import com.leoapps.eggy.setup.domain.model.EggBoilingType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private const val TIMER_UPDATE_INTERVAL = 200L
private const val MAX_PROGRESS = 100
private const val CHANNEL_ID = "eggy_channel_id"
private const val PROGRESS_NOTIFICATION_ID = 1

class BoilProgressService : Service() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val binder = MyBinder()

    var timer: CountDownTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
        Log.d("MyTag", "onDestroy")
    }

    private fun buildNotification(
        timeLeft: Long,
        progress: Int,
        eggType: EggBoilingType
    ): Notification {
        val title = when (eggType) {
            EggBoilingType.SOFT -> getString(R.string.common_soft_boiled_eggs)
            EggBoilingType.MEDIUM -> getString(R.string.common_medium_boiled_eggs)
            EggBoilingType.HARD -> getString(R.string.common_hard_boiled_eggs)
        }
        val iconResId = when (eggType) {
            EggBoilingType.SOFT -> R.drawable.egg_soft
            EggBoilingType.MEDIUM -> R.drawable.egg_medium
            EggBoilingType.HARD -> R.drawable.egg_hard
        }
        val message = getString(
            R.string.notification_progress_message,
            convertMsToText(timeLeft)
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setCategory(Notification.CATEGORY_PROGRESS)
            .setProgress(MAX_PROGRESS, progress, false)
            .setSmallIcon(R.drawable.ic_timer_grey)
            .setLargeIcon(BitmapFactory.decodeResource(resources, iconResId))
            .setOngoing(true)
            .build()
    }

    private var ticks = 0

    private fun onStartTimer(
        calculatedTime: Long,
        eggType: EggBoilingType
    ) {
        ServiceCompat.startForeground(
            this@BoilProgressService,
            PROGRESS_NOTIFICATION_ID,
            buildNotification(
                timeLeft = calculatedTime,
                progress = 0,
                eggType = eggType
            ),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )

        timer?.cancel()
        timer = object : CountDownTimer(calculatedTime, TIMER_UPDATE_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val timePassed = calculatedTime - millisUntilFinished
                coroutineScope.launch {
                    binder.state.emit(
                        TimerStatusUpdate.Progress(timePassed)
                    )
                }

                if (ticks % 5 == 0) {
                    if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        val notification = buildNotification(
                            timeLeft = millisUntilFinished,
                            progress = ((timePassed) / calculatedTime.toFloat() * MAX_PROGRESS).toInt(),
                            eggType = eggType
                        )
                        NotificationManagerCompat
                            .from(this@BoilProgressService)
                            .notify(PROGRESS_NOTIFICATION_ID, notification)
                    }
                    ticks = 0
                }

                ticks++
            }

            override fun onFinish() {
                stopForeground(STOP_FOREGROUND_REMOVE)
                coroutineScope.launch {
                    binder.state.emit(TimerStatusUpdate.Finished)
                }
            }
        }
        timer?.start()
    }


    private fun onStopTimer() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        timer?.cancel()
        coroutineScope.launch {
            binder.state.emit(TimerStatusUpdate.Canceled)
        }
    }

    inner class MyBinder : Binder() {
        val state = MutableSharedFlow<TimerStatusUpdate>()

        fun startTimer(
            calculatedTime: Long,
            eggType: EggBoilingType,
        ) {
            onStartTimer(calculatedTime, eggType)
        }

        fun stopTimer() {
            onStopTimer()
        }
    }
}

sealed interface TimerStatusUpdate {
    object Finished : TimerStatusUpdate
    object Canceled : TimerStatusUpdate
    data class Progress(
        val valueMs: Long
    ) : TimerStatusUpdate
}

