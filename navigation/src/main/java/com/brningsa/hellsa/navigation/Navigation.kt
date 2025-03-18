package com.brningsa.hellsa.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import com.brningsa.hellsa.navigation.internal.InternalNavigationState
import com.brningsa.hellsa.navigation.internal.RouteRecord
import com.brningsa.hellsa.navigation.internal.ScreenMultiStack
import com.brningsa.hellsa.navigation.internal.ScreenStack
import com.brningsa.hellsa.navigation.links.DeepLinksHandler
import com.brningsa.hellsa.navigation.links.MultiStackState
import com.brningsa.hellsa.navigation.links.StackState
import kotlinx.collections.immutable.ImmutableList

data class Navigation(
    val router: Router,
    val navigationState: NavigationState,
    val internalNavigationState: InternalNavigationState
)

@SuppressLint("ContextCastToActivity")
@Composable
fun rememberNavigation(
    rootRoutes: ImmutableList<Route>,
    initialIndex: Int = 0,
    deepLinksHandler: DeepLinksHandler = DeepLinksHandler.DEFAULT
): Navigation {
    val activity = LocalContext.current as? Activity
    val screenStack = rememberSaveable(rootRoutes) {
        val inputState = MultiStackState(
            activeStackIndex = initialIndex,
            stacks = rootRoutes.map { rootRoute -> StackState(listOf(rootRoute)) }
        )

        val outputState = activity?.intent?.data?.let { deepLinkUri ->
            deepLinksHandler.handleDeepLink(deepLinkUri, inputState)
        } ?: inputState

        ScreenMultiStack(
            initialIndex = outputState.activeStackIndex,
            stacks = outputState.stacks.map { stackState ->
                ScreenStack(stackState.routes)
            }.toMutableStateList()
        )

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