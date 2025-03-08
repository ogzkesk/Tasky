package com.ogzkesk.template.navigation

sealed interface NavigationEvent {
    data object Back : NavigationEvent

    data class Navigate<T : Any>(val route: T) : NavigationEvent

    data class NavigateAndPop<T : Any>(val route: T) : NavigationEvent
}
