package com.ogzkesk.tasky.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenRoute

@Serializable
object HomeScreenRoute

@Serializable
object CreationScreenRoute

@Serializable
data class DetailScreenRoute(
    val id: Long,
)

@Serializable
object SettingsScreenRoute
