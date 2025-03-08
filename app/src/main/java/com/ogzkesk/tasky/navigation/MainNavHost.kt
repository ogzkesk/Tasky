package com.ogzkesk.tasky.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.tasky.ui.creation.CreationScreen
import com.ogzkesk.tasky.ui.creation.CreationScreenViewModel
import com.ogzkesk.tasky.ui.detail.DetailScreen
import com.ogzkesk.tasky.ui.detail.DetailScreenViewModel
import com.ogzkesk.tasky.ui.home.HomeScreen
import com.ogzkesk.tasky.ui.home.HomeScreenViewModel
import com.ogzkesk.tasky.ui.settings.SettingsScreen
import com.ogzkesk.tasky.ui.settings.SettingsScreenViewModel
import com.ogzkesk.tasky.ui.splash.SplashScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    Surface {
        NavHost(
            navController = navController,
            startDestination = SplashScreenRoute
        ) {
            composable<SplashScreenRoute> {
                SplashScreen(navController = navController)
            }

            composable<HomeScreenRoute> {
                val viewModel: HomeScreenViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<CreationScreenRoute> {
                val viewModel: CreationScreenViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                CreationScreen(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<DetailScreenRoute> {
                val viewModel: DetailScreenViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                DetailScreen(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<SettingsScreenRoute> {
                val viewModel: SettingsScreenViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                SettingsScreen(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}