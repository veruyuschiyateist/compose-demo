package com.brningsa.hellsa.navigation.internal

import kotlinx.coroutines.flow.Flow

sealed class NavigationEvent {
    data class Removed(val route: RouteRecord) : NavigationEvent()
}

interface InternalNavigationState {
    val currentUuid: String
    fun listen(): Flow<NavigationEvent>
}