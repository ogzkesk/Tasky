package com.ogzkesk.tasky.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.domain.prefs.model.Preferences
import com.ogzkesk.domain.prefs.model.Theme
import com.ogzkesk.tasky.navigation.MainNavHost
import com.ogzkesk.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefs by preferencesRepository.getPreferences().collectAsStateWithLifecycle(
                Preferences()
            )
            TaskyTheme(
                darkTheme = when (prefs.theme.mode) {
                    Theme.Mode.Light -> false
                    Theme.Mode.Dark -> true
                    Theme.Mode.System -> isSystemInDarkTheme()
                },
                dynamicColor = prefs.theme.dynamicColor,
                primaryColor = prefs.theme.primaryColorHex?.let { Color(it) }
            ) {
                MainNavHost()
            }
        }
    }
}
