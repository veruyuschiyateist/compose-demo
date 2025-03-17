package com.brningsa.hellsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.brningsa.hellsa.navigation.internal.InternalNavigationState
import com.brningsa.hellsa.navigation.internal.RouteRecord
import com.brningsa.hellsa.navigation.internal.ScreenMultiStack
import com.brningsa.hellsa.navigation.internal.ScreenStack
import kotlinx.collections.immutable.ImmutableList

data class Navigation(
    val router: Router,
    val navigationState: NavigationState,
    val internalNavigationState: InternalNavigationState
)

@Composable
fun rememberNavigation(
    rootRoutes: ImmutableList<Route>,
    initialIndex: Int = 0
): Navigation {
    val screenStack = rememberSaveable(rootRoutes) {
        val stacks = SnapshotStateList<ScreenStack>()
        stacks.addAll(rootRoutes.map(::ScreenStack))
        ScreenMultiStack(stacks, initialIndex)
    }

    return remember(rootRoutes) {
        Navigation(
            router = screenStack,
            navigationState = screenStack,
            internalNavigationState = screenStack
        )
    }
}