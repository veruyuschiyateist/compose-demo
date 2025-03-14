package com.brningsa.hellsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.brningsa.hellsa.navigation.internal.InternalNavigationState
import com.brningsa.hellsa.navigation.internal.RouteRecord
import com.brningsa.hellsa.navigation.internal.ScreenStack

data class Navigation(
    val router: Router,
    val navigationState: NavigationState,
    val internalNavigationState: InternalNavigationState
)

@Composable
fun rememberNavigation(initialRoute: Route): Navigation {
    val screenStack = rememberSaveable(initialRoute) {
        ScreenStack(mutableStateListOf(RouteRecord(initialRoute)))
    }

    return remember(initialRoute) {
        Navigation(
            router = screenStack,
            navigationState = screenStack,
            internalNavigationState = screenStack
        )
    }
}