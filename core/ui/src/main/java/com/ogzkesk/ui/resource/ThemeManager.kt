package com.ogzkesk.ui.resource

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.ogzkesk.domain.prefs.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ThemeManager {
    private val mutableTheme = MutableStateFlow(Theme.SYSTEM)
    val theme: StateFlow<Theme> = mutableTheme

    fun setTheme(newTheme: Theme) {
        mutableTheme.value = newTheme
    }

    @Composable
    fun isAppInDarkTheme(): Boolean {
        return when (theme.collectAsState().value) {
            Theme.SYSTEM -> isSystemInDarkTheme()
            Theme.LIGHT -> false
            Theme.DARK -> true
        }
    }

    @Suppress("ComposableNaming")
    @Composable
    fun setStatusBarColor(isDefault: Boolean) {
        val view = LocalView.current
        val appearance = isAppInDarkTheme().not()
        val window = (view.context as? ComponentActivity)?.window
        SideEffect {
            window?.let {
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = if (isDefault) appearance else false
            }
        }
    }
}
