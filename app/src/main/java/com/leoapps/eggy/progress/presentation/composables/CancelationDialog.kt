package com.leoapps.eggy.progress.presentation.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.leoapps.eggy.R

@Composable
fun CancelationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.progress_cancelation_dialog_title)) },
        text = { Text(stringResource(R.string.progress_cancelation_dialog_subtitle)) },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(stringResource(R.string.common_yes).uppercase())
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.common_no).uppercase())
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}