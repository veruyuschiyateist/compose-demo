package com.brningsa.hellsa.navigation.links

import com.brningsa.hellsa.navigation.Route

data class StackState(
    val routes: List<Route>
) {

    fun withNewRoute(route: Route): StackState = copy(
        routes = routes + route
    )
}