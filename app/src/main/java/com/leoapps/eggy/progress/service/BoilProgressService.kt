package com.leoapps.eggy.progress.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BoilProgressService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyTag", "onBind")
        return null
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


}