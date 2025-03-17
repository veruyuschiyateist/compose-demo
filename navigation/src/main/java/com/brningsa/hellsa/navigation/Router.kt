package com.brningsa.hellsa.navigation

import androidx.compose.runtime.Stable

@Stable
interface Router {

    fun launch(route: Route)

    fun pop(response: Any? = null)

    fun restart(route: Route) = restart(listOf(route))

    fun restart(rootRoutes: List<Route>, initialIndex: Int = 0)

    fun switchStack(index: Int)
}