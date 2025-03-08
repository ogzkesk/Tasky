package com.ogzkesk.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ogzkesk.ui.resource.ThemeManager

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BackgroundLight = Color(0xFFF2F2F7)
val OnBackgroundLight = Color(0xFF000000)
val ContainerLight = Color(0xFFFFFFFF)
val OnContainerLight = Color(0xFF000000)
val ContentLight = Color(0xFFC3C3C3) // topBar,bottomBar,ElevatedX
val DividerLight = Color(0xFF3C3C43).copy(alpha = 0.36F)

val BlueLight = Color(0xFF007AFF)
val BrownLight = Color(0xFFA2845E)
val CyanLight = Color(0xFF32ADE6)
val GreenLight = Color(0xFF34C759)
val IndigoLight = Color(0xFF5856D6)
val MintLight = Color(0xFF00C7BE)
val OrangeLight = Color(0xFFFF9500)
val PinkLight = Color(0xFFFF2D55)
val PurpleLight = Color(0xFFAF52DE)
val RedLight = Color(0xFFFF3B30)
val TealLight = Color(0xFF30B0C7)
val YellowLight = Color(0xFFFFCC00)
val GrayLight = Color(0xFF8E8E93)
val LinkLight = Color(0xFF007AFF)

val BackgroundDark = Color(0xFF1C1C1E)
val OnBackgroundDark = Color(0xFFFFFFFF)
val ContainerDark = Color(0xFF2C2C2E)
val OnContainerDark = Color(0xFFFFFFFF)
val ContentDark = Color(0xFF262626)
val DividerDark = Color(0xFF545458).copy(alpha = 0.66F)

val BlueDark = Color(0xFF0A84FF)
val BrownDark = Color(0xFFAC8E68)
val CyanDark = Color(0xFF64D2FF)
val GreenDark = Color(0xFF30D158)
val IndigoDark = Color(0xFF5E5CE6)
val MintDark = Color(0xFF66D4CF)
val OrangeDArk = Color(0xFFFF9F0A)
val PinkDark = Color(0xFFFF375F)
val PurpleDark = Color(0xFFBF5AF2)
val RedDark = Color(0xFFFF453A)
val TealDark = Color(0xFF40C8E0)
val YellowDark = Color(0xFFFFD60A)
val GrayDark = Color(0xFF8E8E93)
val LinkDark = Color(0xFF0B84FF)

fun Color.asHalfAlpha() = copy(alpha = 0.6f)

fun Color.asLowAlpha() = copy(alpha = 0.2f)

val ColorScheme.contentBlue: Color
    @Composable
    get() = if (ThemeManager.isAppInDarkTheme()) BlueDark else BlueLight
