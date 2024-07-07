package com.leoapps.progress.platform.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.notification.platform.NotificationChannelManager
import com.leoapps.base.utils.convertMsToText
import com.leoapps.progress.platform.service.BoilProgressService
import com.leoapps.shared_res.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoilProgressNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationChannelManager {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    override fun createChannels() {
        val notificationChanel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notificaton_channel_progress_title),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = context.getString(R.string.notificaton_channel_progress_description)
            setShowBadge(true)
        }

        notificationManagerCompat.createNotificationChannel(notificationChanel)
    }

    fun notifyProgress(
        timeLeft: Long,
        totalTime: Long,
        eggType: EggBoilingType
    ) {
        if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notification = getProgressNotification(
                timeLeft = timeLeft,
                totalTime = totalTime,
                eggType = eggType,
            )
            NotificationManagerCompat
                .from(context)
                .notify(PROGRESS_NOTIFICATION_ID, notification)
        }
    }

    fun getProgressNotification(
        timeLeft: Long,
        totalTime: Long,
        eggType: EggBoilingType
    ): Notification {
        val progress = ((totalTime - timeLeft) / totalTime.toFloat() * 100).toInt()

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(getNotificationTitle(eggType))
            .setContentText(getNotificationMessage(timeLeft))
            .setCategory(Notification.CATEGORY_PROGRESS)
            .setProgress(MAX_PROGRESS, progress, false)
            .setSmallIcon(R.drawable.ic_timer_grey)
            .setLargeIcon(getNotificationIcon(eggType))
            .setOngoing(true)
            .addAction(getNotificationAction())
            .build()
    }

    private fun getNotificationMessage(timeLeft: Long) = context.getString(
        R.string.notification_progress_message,
        convertMsToText(timeLeft)
    )

    private fun getNotificationTitle(eggType: EggBoilingType) = when (eggType) {
        EggBoilingType.SOFT -> context.getString(R.string.common_soft_boiled_eggs)
        EggBoilingType.MEDIUM -> context.getString(R.string.common_medium_boiled_eggs)
        EggBoilingType.HARD -> context.getString(R.string.common_hard_boiled_eggs)
    }

    private fun getNotificationIcon(eggType: EggBoilingType): Bitmap {
        val iconResId = when (eggType) {
            EggBoilingType.SOFT -> R.drawable.egg_soft
            EggBoilingType.MEDIUM -> R.drawable.egg_medium
            EggBoilingType.HARD -> R.drawable.egg_hard
        }
        return BitmapFactory.decodeResource(context.resources, iconResId)
    }

    private fun getNotificationAction(): NotificationCompat.Action {
        val actionIcon = IconCompat.createWithResource(context, R.drawable.ic_cancel)
        val actionText = context.getString(R.string.notificaton_progress_action_cancel)

        val intent = Intent(context, BoilProgressService::class.java)
            .putExtra(BoilProgressService.ACTION_CANCEL, true)

        val pendingIntent = PendingIntent.getService(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Action.Builder(actionIcon, actionText, pendingIntent).build()
    }

    companion object {
        const val PROGRESS_NOTIFICATION_ID = 1
        private const val MAX_PROGRESS = 100
        private const val CHANNEL_ID = "eggy_channel_id"
    }
}