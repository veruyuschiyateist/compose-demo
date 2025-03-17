package com.brningsa.hellsa.navigation

import androidx.compose.runtime.Stable

@Stable
interface NavigationState {

    val isRoot: Boolean
    val currentRoute: Route

    val currentScreen: Screen

    val currentStackIndex: Int
}