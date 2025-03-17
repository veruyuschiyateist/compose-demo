package com.brningsa.hellsa.navigation.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.brningsa.hellsa.navigation.NavigationState
import com.brningsa.hellsa.navigation.Route
import com.brningsa.hellsa.navigation.Router
import com.brningsa.hellsa.navigation.Screen
import com.brningsa.hellsa.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class ScreenMultiStack(
    private val stacks: SnapshotStateList<ScreenStack>,
    initialIndex: Int = 0
) : NavigationState, Router, InternalNavigationState, Parcelable {

    override var currentStackIndex: Int by mutableIntStateOf(initialIndex)

    private val currentStack: ScreenStack get() = stacks[currentStackIndex]

    override val isRoot: Boolean
        get() = currentStack.isRoot

    override val currentRoute: Route
        get() = currentStack.currentRoute

    override val currentScreen: Screen
        get() = currentStack.currentScreen

    override val currentUuid: String
        get() = currentStack.currentUuid

    override val screenResponseReceiver: ScreenResponseReceiver
        get() = currentStack.screenResponseReceiver

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    constructor(parcel: Parcel) : this(
        stacks = SnapshotStateList<ScreenStack>().also { newList ->
            ParcelCompat.readList(
                parcel,
                newList,
                ScreenStack::class.java.classLoader,
                ScreenStack::class.java
            )
        },
        initialIndex = parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(stacks)
        dest.writeInt(currentStackIndex)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun launch(route: Route) {
        currentStack.launch(route)
    }

    override fun pop(response: Any?) {
        val routeRecord = currentStack.pop(response)
        if (routeRecord != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(routeRecord))
        }
    }

    override fun restart(route: Route) {
        super.restart(route)
    }

    override fun restart(
        rootRoutes: List<Route>,
        initialIndex: Int
    ) {
        stacks.flatMap { it.getAllRouteRecords() }
            .map { NavigationEvent.Removed(it) }
            .forEach { eventsFlow::tryEmit }
        stacks.clear()
        stacks.addAll(rootRoutes.map(::ScreenStack))
        switchStack(initialIndex)
    }

    override fun switchStack(index: Int) {
        currentStackIndex = index
    }

    override fun listen(): Flow<NavigationEvent> {
        return eventsFlow
    }

    companion object CREATOR : Parcelable.Creator<ScreenMultiStack> {
        override fun createFromParcel(source: Parcel): ScreenMultiStack? {
            return ScreenMultiStack(source)
        }

        override fun newArray(size: Int): Array<out ScreenMultiStack?>? {
            return arrayOfNulls(size)
        }
    }
}