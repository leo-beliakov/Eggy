package com.leoapps.base_ui.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString

@Composable
fun annotatedStringResource(@StringRes id: Int): AnnotatedString {
    val string = stringResource(id)
    return AnnotatedString.fromHtml(string)
}