package com.leoapps.eggy.base.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import com.leoapps.eggy.base.permissions.model.PermissionRequestResult
import com.leoapps.eggy.base.utils.isGranted
import com.leoapps.eggy.base.utils.needsRationale

/**
 * A contract for requesting a single permission and returning the result as a [PermissionRequestResult].
 *
 * @param activity The activity context used to request permissions and check permission rationale.
 */
class RequestPermissionContract(
    private val activity: Activity
) : ActivityResultContract<String, PermissionRequestResult>() {

    private var permission = ""

    override fun createIntent(context: Context, input: String): Intent {
        permission = input
        return Intent(RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS)
            .putExtra(RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(input))
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PermissionRequestResult {
        if (intent == null || resultCode != Activity.RESULT_OK) return PermissionRequestResult.DENIED
        val grantResults =
            intent.getIntArrayExtra(RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS)
        val granted = grantResults?.any { it == PackageManager.PERMISSION_GRANTED } == true
        return granted.mapToPermissionRequestResult()
    }

    override fun getSynchronousResult(
        context: Context,
        input: String
    ): SynchronousResult<PermissionRequestResult>? {
        return if (context.isGranted(input)) {
            SynchronousResult(PermissionRequestResult.GRANTED)
        } else {
            null
        }
    }

    /**
     * Maps a boolean value indicating whether a permission is granted to a [PermissionRequestResult].
     *
     * @return A [PermissionRequestResult] indicating the result of the permission request.
     */
    private fun Boolean.mapToPermissionRequestResult(): PermissionRequestResult = when {
        this -> PermissionRequestResult.GRANTED
        activity.needsRationale(permission) -> PermissionRequestResult.DENIED
        else -> PermissionRequestResult.DONT_ASK_AGAIN
    }
}