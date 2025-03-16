package com.brningsa.hellsa.navigation.internal

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.brningsa.hellsa.navigation.NavigationState
import com.brningsa.hellsa.navigation.Route
import com.brningsa.hellsa.navigation.Router
import com.brningsa.hellsa.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ParcelCreator")
internal class ScreenStack(
    private val routes: SnapshotStateList<RouteRecord>
) : NavigationState, Router, InternalNavigationState, Parcelable {

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    override val isRoot: Boolean
        get() = routes.size == 1

    override val currentRoute: Route
        get() = routes.last().route

    override val currentScreen: Screen by derivedStateOf {
        currentRoute.screenProducer()
    }

    override val currentUuid: String
        get() = routes.last().uuid

    override fun launch(route: Route) {
        routes.add(RouteRecord(route))
    }

    override fun pop() {
        val removedRoute = routes.removeLastOrNull()
        if (removedRoute != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(removedRoute))
        }
    }

    override fun restart(route: Route) {
        routes.apply {
            routes.forEach {
                eventsFlow.tryEmit(NavigationEvent.Removed(route = it))
            }
            clear()
            add(RouteRecord(route))
        }
    }

    constructor(parcel: Parcel) : this(
        SnapshotStateList<RouteRecord>().also { newList ->
            ParcelCompat.readList<RouteRecord>(
                parcel,
                newList,
                RouteRecord::class.java.classLoader,
                RouteRecord::class.java
            )
        }
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(routes)
    }

    override fun listen(): Flow<NavigationEvent> {
        return eventsFlow
    }
}