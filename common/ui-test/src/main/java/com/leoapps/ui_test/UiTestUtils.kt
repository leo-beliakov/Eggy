package com.leoapps.ui_test

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule

fun AndroidComposeTestRule<*, *>.getString(@StringRes id: Int) = activity.getString(id)