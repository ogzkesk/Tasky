package com.ogzkesk.tasky.ui.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    navController: NavHostController,
    state: CreationScreenState,
    onEvent: (CreationScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.creation_screen_title),
                onBack = navController::popBackStack
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
    }
}

@ThemedPreviews
@Composable
fun CreationScreenPreview() {
    TaskyTheme {
        CreationScreen(
            navController = rememberNavController(),
            state = CreationScreenState(),
            onEvent = {}
        )
    }
}