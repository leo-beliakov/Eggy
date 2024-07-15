package com.leoapps.progress.platform.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.progress.platform.notification.BoilProgressNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIMER_UPDATE_INTERVAL = 200L

//We cannot update notifications too often. Otherwise the OS will throttle our notifications
private const val NOTIFICATION_UPDATE_INTERVAL = 1000L


@AndroidEntryPoint
class BoilProgressService : Service() {

    @Inject
    lateinit var notificationManager: BoilProgressNotificationManager

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val binder = MyBinder()

    var timer: CountDownTimer? = null
    var boilingTime = 0L
    var eggType = EggBoilingType.MEDIUM

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras?.getBoolean(ACTION_CANCEL) == true) {
            onStopTimer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun onStartTimer(
        boilingTime: Long,
        eggType: EggBoilingType
    ) {
        this.boilingTime = boilingTime
        this.eggType = eggType

        ServiceCompat.startForeground(
            this,
            BoilProgressNotificationManager.PROGRESS_NOTIFICATION_ID,
            notificationManager.getProgressNotification(
                timeLeft = boilingTime,
                totalTime = boilingTime,
                eggType = eggType
            ),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )

        timer?.cancel()
        timer = twoTicksTimer(
            millisInFuture = boilingTime,
            shortTickInterval = TIMER_UPDATE_INTERVAL,
            longTickInterval = NOTIFICATION_UPDATE_INTERVAL,
            onShortTick = ::updateProgress,
            onLongTick = ::updateNotification,
            onTimerFinished = ::onTimerFinished,
        )
        timer?.start()
    }

    private fun updateProgress(millisUntilFinished: Long) {
        val timePassed = boilingTime - millisUntilFinished
        coroutineScope.launch {
            binder.state.emit(
                TimerStatusUpdate.Progress(timePassed)
            )
        }
    }

    private fun updateNotification(millisUntilFinished: Long) {
        notificationManager.notifyProgress(
            timeLeft = millisUntilFinished,
            totalTime = boilingTime,
            eggType = eggType
        )
    }

    private fun onTimerFinished() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        coroutineScope.launch {
            binder.state.emit(TimerStatusUpdate.Finished)
        }
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

        fun startTimer(calculatedTime: Long, eggType: EggBoilingType) {
            onStartTimer(calculatedTime, eggType)
        }

        fun stopTimer() {
            onStopTimer()
        }
    }

    companion object {
        const val ACTION_CANCEL = "leo_apps_eggy_action_cancel"
    }
}

sealed interface TimerStatusUpdate {
    object Finished : TimerStatusUpdate
    object Canceled : TimerStatusUpdate
    data class Progress(
        val valueMs: Long
    ) : TimerStatusUpdate
}

