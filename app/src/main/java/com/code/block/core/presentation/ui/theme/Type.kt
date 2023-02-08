package com.code.block.core.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.code.block.R

val quicksand = FontFamily(
    Font(R.font.quicksandlight, FontWeight.Light),
    Font(R.font.quicksandregular, FontWeight.Normal),
    Font(R.font.quicksandmedium, FontWeight.Medium),
    Font(R.font.quicksandsemibold, FontWeight.SemiBold),
    Font(R.font.quicksandbold, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = TextWhite,
    ),
    h1 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        color = TextWhite,
    ),
    h2 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = TextWhite,
    ),
    body2 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextWhite,
    ),
)
