package com.leoapps.eggy.progress.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

private const val TIMER_UPDATE_INTERVAL = 100L

class BoilProgressService : Service() {

    private val binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTag", "onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d("MyTag", "onStart")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyTag", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyTag", "onDestroy")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("MyTag", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d("MyTag", "onRebind")
        super.onRebind(intent)
    }

    var timer: CountDownTimer? = null

    private fun onStartTimer(calculatedTime: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(calculatedTime, TIMER_UPDATE_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                binder.state.value = TimerStatusUpdate.Progress(
                    calculatedTime - millisUntilFinished
                )
            }

            override fun onFinish() {
                binder.state.value = TimerStatusUpdate.Finished
            }
        }
        timer?.start()
    }


    private fun onStopTimer() {
        timer?.cancel()
        binder.state.value = TimerStatusUpdate.Canceled
    }


    inner class MyBinder : Binder() {

        val state = MutableStateFlow<TimerStatusUpdate>(TimerStatusUpdate.Progress(0))

        fun startTimer(calculatedTime: Long) = onStartTimer(calculatedTime)

        fun stopTimer() = onStopTimer()
    }
}

sealed interface TimerStatusUpdate {
    object Finished : TimerStatusUpdate
    object Canceled : TimerStatusUpdate
    data class Progress(
        val valueMs: Long
    ) : TimerStatusUpdate
}

