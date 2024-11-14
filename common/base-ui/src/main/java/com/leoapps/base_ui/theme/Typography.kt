package com.leoapps.base_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.leoapps.base_ui.R

val MonsterratFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.monsterrat_extrabold,
            weight = FontWeight.W900,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.monsterrat_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.monsterrat_regular,
            weight = FontWeight.W500,
            style = FontStyle.Normal
        )
    )
)

val TitanOneFamily = FontFamily(
    fonts = listOf(
        Font(resId = R.font.titan_one)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayMedium = TextStyle(
        fontWeight = FontWeight.W900,
        fontFamily = TitanOneFamily,
        textAlign = TextAlign.Center,
        fontSize = 46.sp,
        lineHeight = 36.sp,
        color = Black,
    ),
    displaySmall = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.W900,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        color = Black
    ),
    headlineLarge = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.W900,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = -0.5.sp,
        color = Black
    ),
    headlineMedium = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.W900,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = -0.5.sp,
        color = Black
    ),
    headlineSmall = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.3.sp,
        color = Black
    ),
    titleLarge = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp,
        color = Black
    ),
    titleMedium = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp,
        color = Black
    ),
    titleSmall = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.2.sp,
        lineHeight = 20.sp,
        color = GrayLight
    ),
    bodyLarge = TextStyle(
        fontFamily = MonsterratFontFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontSize = 12.sp,
        lineHeight = 20.sp,
    )
)
