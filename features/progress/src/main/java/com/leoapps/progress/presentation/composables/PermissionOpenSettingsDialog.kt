package com.leoapps.progress.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.leoapps.base_ui.components.dialog.Dialog
import com.leoapps.shared_res.R

@Composable
fun PermissionOpenSettingsDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    title = stringResource(R.string.progress_rationale_dialog_title),
    subtitle = stringResource(R.string.progress_rationale_dialog_subtitle),
    positiveButtonText = stringResource(R.string.progress_rationale_dialog_go_to_settings),
    onPositiveButtonClicked = onConfirm,
    onDismissButtonClicked = onDismiss,
)