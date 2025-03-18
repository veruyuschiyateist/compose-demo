package com.brningsa.hellsa.navigation.internal

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.core.os.ParcelCompat
import com.brningsa.hellsa.navigation.NavigationState
import com.brningsa.hellsa.navigation.Route
import com.brningsa.hellsa.navigation.Router
import com.brningsa.hellsa.navigation.Screen
import com.brningsa.hellsa.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ParcelCreator")
internal class ScreenStack(
    private val routes: SnapshotStateList<RouteRecord>,
    private val screenResponseReceiverBus: ScreenResponseReceiverBus = ScreenResponseReceiverBus()
) : Parcelable {


    val isRoot: Boolean
        get() = routes.size == 1

    val currentRoute: Route
        get() = routes.last().route

    val currentScreen: Screen by derivedStateOf {
        currentRoute.screenProducer()
    }

    val currentUuid: String
        get() = routes.last().uuid

    val screenResponseReceiver: ScreenResponseReceiver = screenResponseReceiverBus

    fun launch(route: Route) {
        screenResponseReceiverBus.cleanUp()
        routes.add(RouteRecord(route))
    }

    fun pop(response: Any?): RouteRecord? {
        val removedRoute = routes.removeLastOrNull()
        if (removedRoute != null) {
            if (response != null) {
                screenResponseReceiverBus.send(response)
            }
        }

        return removedRoute
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

    constructor(routes: List<Route>) : this(
        routes.map(::RouteRecord).toMutableStateList()
    )

    constructor(rootRoute: Route) : this(
        routes = mutableStateListOf(RouteRecord(rootRoute))
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(routes)
    }

    fun getAllRouteRecords(): List<RouteRecord> = routes
}