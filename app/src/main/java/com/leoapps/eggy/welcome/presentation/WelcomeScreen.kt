package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.leoapps.eggy.R
import com.leoapps.eggy.base.presentation.EggyTheme
import com.leoapps.eggy.base.presentation.Primary
import com.leoapps.eggy.base.presentation.White
import com.leoapps.eggy.base.presentation.dimens
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreenDestination

@Composable
fun WelcomeScreen(
    onContinueClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = MaterialTheme.dimens.screenPaddingL)
    ) {
        val (title1, title2, title3, image, button) = createRefs()
        val titleChain = createVerticalChain(title1, title2, title3, chainStyle = ChainStyle.Packed)
        constrain(titleChain) {
            top.linkTo(parent.top)
            bottom.linkTo(image.top)
        }

        Text(
            text = stringResource(R.string.welcome_title_1),
            style = MaterialTheme.typography.headlineMedium,
            color = Primary,
            modifier = Modifier.constrainAs(title1) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Text(
            text = stringResource(R.string.welcome_title_2),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.constrainAs(title2) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Text(
            text = stringResource(R.string.welcome_title_3),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.constrainAs(title3) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Image(
            painter = painterResource(R.drawable.welcome_egg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.percent(0.7f)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0.6f)
                linkTo(start = parent.start, end = parent.end)
            }
        )
        ElevatedButton(
            onClick = onContinueClicked,
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = MaterialTheme.dimens.elevationM,
                pressedElevation = MaterialTheme.dimens.elevationL,
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = MaterialTheme.dimens.buttonHeight)
                .constrainAs(button) {
                    top.linkTo(image.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(R.string.welcome_button_continue),
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontWeight = FontWeight.W700,
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    EggyTheme {
        WelcomeScreen(
            onContinueClicked = {}
        )
    }
}