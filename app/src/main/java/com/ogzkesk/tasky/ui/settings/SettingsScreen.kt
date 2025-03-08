package com.ogzkesk.tasky.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews

@Composable
fun SettingsScreen(
    navController: NavHostController,
    state: SettingsScreenState,
    onEvent: (SettingsScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskyTopBar(title = stringResource(R.string.settings_screen_title))
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
    }
}

@ThemedPreviews
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        SettingsScreen(
            navController = rememberNavController(),
            state = SettingsScreenState(),
            onEvent = {}
        )
    }
}