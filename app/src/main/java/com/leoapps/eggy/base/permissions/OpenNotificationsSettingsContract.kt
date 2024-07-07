package com.leoapps.eggy.base.permissions

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract

/**
 * An [ActivityResultContract] to open the notification settings for the current app.
 */
class OpenNotificationsSettingsContract :
    ActivityResultContract<Unit, Unit>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName())
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = Unit

    override fun getSynchronousResult(
        context: Context,
        input: Unit
    ): SynchronousResult<Unit>? = null
}