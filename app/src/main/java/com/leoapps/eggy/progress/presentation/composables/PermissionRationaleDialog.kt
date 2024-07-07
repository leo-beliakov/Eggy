package com.leoapps.eggy.progress.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.leoapps.eggy.R
import com.leoapps.eggy.base.ui.dialog.Dialog

@Composable
fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    title = stringResource(R.string.progress_rationale_dialog_title),
    subtitle = stringResource(R.string.progress_rationale_dialog_subtitle),
    positiveButtonText = stringResource(R.string.progress_rationale_dialog_allow),
    onPositiveButtonClicked = onConfirm,
    onDismissButtonClicked = onDismiss,
)