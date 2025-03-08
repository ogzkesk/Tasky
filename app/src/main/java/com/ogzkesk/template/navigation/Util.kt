package com.ogzkesk.template.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.template.ui.app.AppState
import com.ogzkesk.ui.resource.ScreenTransition
import com.ogzkesk.ui.resource.ThemeManager
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    appContentRepository: AppContentRepository,
) = remember {
    AppState(
        coroutineScope = coroutineScope,
        navController = navController,
        appContentRepository = appContentRepository,
    )
}

inline fun <reified T : Any> NavGraphBuilder.screen(
    isDefaultStatusBar: Boolean = true,
    screenTransition: ScreenTransition = ScreenTransition.default(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { screenTransition.enterTransition },
        exitTransition = { screenTransition.exitTransition },
        popEnterTransition = { screenTransition.popEnterTransition },
        popExitTransition = { screenTransition.popExitTransition },
        content = {
            ThemeManager.setStatusBarColor(isDefaultStatusBar)
            content(it)
        },
    )
}
