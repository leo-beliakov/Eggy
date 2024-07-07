package com.leoapps.eggy.base.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun Dialog(
    title: String,
    subtitle: String,
    positiveButtonText: String,
    dismissButtonText: String? = null,
    onPositiveButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit,
    dismissOnOutsideClick: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismissButtonClicked,
        title = { Text(title) },
        text = { Text(subtitle) },
        confirmButton = {
            TextButton(onClick = onPositiveButtonClicked) {
                Text(positiveButtonText.uppercase())
            }
        },
        dismissButton = {
            dismissButtonText?.let {
                TextButton(onClick = onDismissButtonClicked) {
                    Text(dismissButtonText.uppercase())
                }
            }
        },
        properties = DialogProperties(dismissOnClickOutside = dismissOnOutsideClick)
    )
}