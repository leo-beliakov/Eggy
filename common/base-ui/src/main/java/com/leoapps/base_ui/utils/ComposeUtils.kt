package com.leoapps.base_ui.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun CurrentActivity(): Activity {
    return LocalContext.current as Activity
}