package com.leoapps.eggy.progress.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoapps.eggy.R
import com.leoapps.eggy.base.presentation.Primary
import com.leoapps.eggy.base.presentation.White
import com.leoapps.eggy.base.presentation.dimens
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import kotlinx.serialization.Serializable


@Serializable
object BoilProgressScreenDestination

@Composable
fun BoilProgressScreen(
    viewModel: BoilProgressViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoilProgressScreen(
        state = state,
        onBackClicked = onBackClicked,
    )
}

@Composable
private fun BoilProgressScreen(
    state: BoilProgressUiState,
    onBackClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(
                vertical = MaterialTheme.dimens.screenPaddingXL,
                horizontal = MaterialTheme.dimens.screenPaddingL
            )
    ) {
        Toolbar()
        TimerSection()
        ParametersSection()
        TipsSection()
        Spacer(modifier = Modifier.weight(1f, true))
        ButtonStartSection()

    }
}

@Composable
private fun Toolbar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {

            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null
            )
        }
        Text(
            text = "Boiled eggs",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                // reserves the size of the icon button
                // to prevent overlapping:
                .padding(horizontal = 48.dp)
                .background(Color.Cyan)
        )
    }
}

@Composable
private fun TimerSection() {

}

@Composable
private fun ParametersSection() {

}

@Composable
private fun TipsSection() {

}

@Composable
private fun ButtonStartSection() {
    ElevatedButton(
        onClick = { },
        shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = MaterialTheme.dimens.elevationM,
            pressedElevation = MaterialTheme.dimens.elevationL,
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = MaterialTheme.dimens.buttonHeight)
    ) {
        Text(
            text = stringResource(R.string.progress_button_start),
            style = MaterialTheme.typography.titleMedium,
            color = White,
            fontWeight = FontWeight.W700,
        )
    }
}
