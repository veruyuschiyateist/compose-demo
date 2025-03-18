package com.brningsa.hellsa.myapplication

import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.screens.item.ItemScreenArgs
import com.brningsa.hellsa.myapplication.ui.screens.items.ItemsScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.ProfileScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.SettingsScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.item.itemScreenProducer
import com.brningsa.hellsa.navigation.Route
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

sealed class AppRoute(
    override val screenProducer: () -> AppScreen
) : Route {

    @Parcelize
    data class Item(
        val args: ItemScreenArgs
    ) : AppRoute(itemScreenProducer(args))

    sealed class Tab(
        screenProducer: () -> AppScreen
    ) : AppRoute(screenProducer) {

        @Parcelize
        data object Items : Tab(ItemsScreenProducer)

        @Parcelize
        data object Settings : Tab(SettingsScreenProducer)

        @Parcelize
        data object Profile : Tab(ProfileScreenProducer)
    }
}

val AppRouteTabs = persistentListOf(
    AppRoute.Tab.Items, AppRoute.Tab.Settings, AppRoute.Tab.Profile
)