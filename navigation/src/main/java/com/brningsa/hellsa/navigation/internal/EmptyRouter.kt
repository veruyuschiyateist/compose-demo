package com.brningsa.hellsa.navigation.internal

import com.brningsa.hellsa.navigation.Route
import com.brningsa.hellsa.navigation.Router

internal object EmptyRouter : Router {

    override fun launch(route: Route) = Unit

    override fun pop(response: Any?) = Unit

    override fun restart(route: Route) = Unit
    override fun restart(
        rootRoutes: List<Route>,
        initialIndex: Int
    ) = Unit

    override fun switchStack(index: Int) = Unit
}