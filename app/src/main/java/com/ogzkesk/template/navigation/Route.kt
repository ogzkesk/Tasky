package com.ogzkesk.template.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenRoute

@Serializable
object FirstScreenRoute

@Serializable
data class SecondScreenRoute(
    val arg: String,
)
