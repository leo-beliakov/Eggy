package com.leoapps.progress

import android.content.Context
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base_ui.theme.EggyTheme
import com.leoapps.progress.presentation.BoilProgressScreen
import com.leoapps.progress.presentation.BoilProgressViewModel
import com.leoapps.shared_res.R
import com.leoapps.ui_test.HiltTestActivity
import com.leoapps.ui_test.getString
import com.leoapps.vibration.presentation.LocalVibrationManager
import com.leoapps.waterapp.common.vibrator.domain.VibrationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProgressScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    // Apparently, the only way to set a SavedStateHandle with navigation arguments in UI Tests
    // is to manually create a ViewModel instance with all its dependencies:
    lateinit var viewModel: BoilProgressViewModel

    @Inject
    @ApplicationContext
    lateinit var context: Context

    val vibrationManager: VibrationManager = mockk(relaxed = true)
    val onBackClicked: () -> Unit = mockk(relaxed = true)

    @Before
    fun setup() {

        hiltRule.inject()
        viewModel = BoilProgressViewModel(
            context = context,
            vibrationManager = vibrationManager,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    "type" to EggBoilingType.MEDIUM.name,
                    "calculatedTime" to 12435L,
                )
            )
        )

        composeRule.setContent {
            EggyTheme {
                CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
                    BoilProgressScreen(
                        viewModel = viewModel,
                        onBackClicked = onBackClicked
                    )
                }
            }
        }
    }

    @Test
    fun clickBackArrow_shouldNavigateBack() {
        // Arrange
        val backButton = composeRule.onNodeWithContentDescription(
            composeRule.getString(R.string.common_back)
        )

        //Act
        backButton.performClick()

        //Assert
        verify { onBackClicked.invoke() }
    }
}