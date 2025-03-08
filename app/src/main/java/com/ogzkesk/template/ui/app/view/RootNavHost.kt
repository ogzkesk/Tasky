package com.ogzkesk.template.ui.app.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.ogzkesk.template.navigation.FirstScreenRoute
import com.ogzkesk.template.navigation.SecondScreenRoute
import com.ogzkesk.template.navigation.SplashScreenRoute
import com.ogzkesk.template.navigation.screen
import com.ogzkesk.template.ui.app.AppState
import com.ogzkesk.template.ui.first.FirstScreen
import com.ogzkesk.template.ui.second.SecondScreen
import com.ogzkesk.template.ui.splash.SplashScreen

@Composable
fun RootNavHost(appState: AppState) {
    NavHost(
        navController = appState.navController,
        startDestination = SplashScreenRoute,
    ) {
        screen<SplashScreenRoute> {
            SplashScreen()
        }
        screen<FirstScreenRoute> {
            FirstScreen()
        }
        screen<SecondScreenRoute> {
            SecondScreen()
        }
    }
}
