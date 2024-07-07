package com.leoapps.progress.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.leoapps.base_ui.components.dialog.Dialog
import com.leoapps.shared_res.R

@Composable
fun CancelationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    title = stringResource(R.string.progress_cancelation_dialog_title),
    subtitle = stringResource(R.string.progress_cancelation_dialog_subtitle),
    positiveButtonText = stringResource(R.string.common_yes),
    dismissButtonText = stringResource(R.string.common_no),
    onPositiveButtonClicked = onConfirm,
    onDismissButtonClicked = onDismiss,
)
