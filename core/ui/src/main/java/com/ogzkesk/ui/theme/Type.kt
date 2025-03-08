package com.ogzkesk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ogzkesk.ui.R

val Typography = Typography(
    displayLarge = CustomTypography.H1,
    displayMedium = CustomTypography.H2,
    displaySmall = CustomTypography.H3,
    headlineLarge = CustomTypography.H1,
    headlineMedium = CustomTypography.H2,
    headlineSmall = CustomTypography.H3,
    titleLarge = CustomTypography.H4,
    titleMedium = CustomTypography.H5,
    titleSmall = CustomTypography.H6,
    bodyLarge = CustomTypography.P18,
    bodyMedium = CustomTypography.P16,
    bodySmall = CustomTypography.H7,
    labelLarge = CustomTypography.P14,
    labelMedium = CustomTypography.P12,
    labelSmall = CustomTypography.P10,
)

object CustomTypography {
    val sfProDisplay = FontFamily(
        Font(resId = R.font.sfpro_display_bold, weight = FontWeight.Black),
        Font(resId = R.font.sfpro_display_bold, weight = FontWeight.Bold),
        Font(resId = R.font.sf_pro_display_semibold, weight = FontWeight.SemiBold),
        Font(resId = R.font.sfpro_display_regular, weight = FontWeight.Medium),
        Font(resId = R.font.sfpro_display_regular, weight = FontWeight.Normal),
        Font(resId = R.font.sfpro_display_regular, weight = FontWeight.Light),
    )
    val sfProText = FontFamily(
        Font(resId = R.font.sf_pro_text_bold, weight = FontWeight.Black),
        Font(resId = R.font.sf_pro_text_bold, weight = FontWeight.Bold),
        Font(resId = R.font.sf_pro_text_semibold, weight = FontWeight.SemiBold),
        Font(resId = R.font.sf_pro_text_regular, weight = FontWeight.Medium),
        Font(resId = R.font.sf_pro_text_regular, weight = FontWeight.Normal),
        Font(resId = R.font.sf_pro_text_regular, weight = FontWeight.Light),
    )

    val H1 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 40.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 56.sp,
        letterSpacing = (0.8).sp,
    )

    val H2 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 44.sp,
        letterSpacing = (0.7).sp,
    )

    val H3 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp,
        letterSpacing = (0.5).sp,
    )

    val H4 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = (0.4).sp,
    )

    val H5 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp,
        letterSpacing = (0.3).sp,
    )

    val H6 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = (0.3).sp,
    )

    val H7 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        letterSpacing = (0.25).sp,
    )

    val H8 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 14.sp,
        letterSpacing = (0.2).sp,
    )

    val P40 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 40.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 56.sp,
        letterSpacing = (0.8).sp,
    )

    val P32 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp,
        letterSpacing = (0.7).sp,
    )

    val P24 = TextStyle(
//            fontFamily = sfProDisplay,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 34.sp,
        letterSpacing = (0.5).sp,
    )

    val P18 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
        letterSpacing = (0.4).sp,
    )

    val P16 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
        letterSpacing = (0.4).sp,
    )

    val P14 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (0.3).sp,
    )

    val P12 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = (0.3).sp,
    )

    val P10 = TextStyle(
//            fontFamily = sfProText,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 12.sp,
        letterSpacing = (0.2).sp,
    )
}

fun TextStyle.asSemiBold() = copy(fontWeight = FontWeight.SemiBold)

fun TextStyle.asBold() = copy(fontWeight = FontWeight.Bold)

fun TextStyle.asLight() = copy(fontWeight = FontWeight.Light)
