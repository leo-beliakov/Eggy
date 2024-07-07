package com.leoapps.base.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.leoapps.base.permissions.model.PermissionStatus

/**
 * Resolves the permission status based on the permission request result.
 *
 * @param activity The [Activity] in which the permission check is performed.
 * @param isGranted The result of the permission request.
 * @param permission The permission string to check.
 * @return A [PermissionStatus] indicating the result of the permission check.
 */
fun resolvePermissionStatus(
    activity: Activity,
    isGranted: Boolean,
    permission: String,
): PermissionStatus = when {
    isGranted -> PermissionStatus.GRANTED
    activity.needsRationale(permission) -> PermissionStatus.DENIED
    else -> PermissionStatus.DONT_ASK_AGAIN
}

/**
 * Resolves the permission status based on the given activity and permission string.
 *
 * @param activity The [Activity] in which the permission check is performed.
 * @param permission The permission string to check.
 * @return A [PermissionStatus] indicating the result of the permission check.
 */
fun resolvePermissionStatus(
    activity: Activity,
    permission: String,
): PermissionStatus {
    return resolvePermissionStatus(
        activity = activity,
        isGranted = activity.isGranted(permission),
        permission = permission,
    )
}

fun Context.isGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.needsRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}