package com.ogzkesk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.ogzkesk.domain.prefs.model.Theme
import com.ogzkesk.ui.resource.ThemeManager

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = BackgroundDark,
    onSurface = OnBackgroundDark,
    surfaceVariant = ContainerDark,
    onSurfaceVariant = OnContainerDark,
    surfaceTint = ContentDark,
    outlineVariant = DividerDark,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = BackgroundLight,
    onSurface = OnBackgroundLight,
    surfaceVariant = ContainerLight,
    onSurfaceVariant = OnContainerLight,
    surfaceTint = ContentLight,
    outlineVariant = DividerLight,
)

@Composable
fun TemplateTheme(
    theme: Theme = Theme.SYSTEM,
    content: @Composable () -> Unit,
) {
    ThemeManager.setTheme(theme)

    val darkTheme = when (theme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.DARK -> true
        Theme.LIGHT -> false
    }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )
}
