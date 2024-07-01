package com.leoapps.eggy.progress.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

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

    //todo find alternative
    val countDownTimer = object : CountDownTimer(
        4000L,
        1000L
    ) {
        override fun onTick(millisUntilFinished: Long) {
            binder.state.value = millisUntilFinished.toInt()
        }

        override fun onFinish() {
            binder.state.value = 0
        }
    }
    var timerIsRunning = false

    inner class MyBinder : Binder() {

        val state = MutableStateFlow(0)

        fun startTimer() {
            countDownTimer.start()
            timerIsRunning = true
            Log.d("MyTag", "startTimer $timerIsRunning")
        }

        fun stopTimer() {
            countDownTimer.cancel()
            timerIsRunning = false
            Log.d("MyTag", "stopTimer $timerIsRunning")
        }
    }
}

