package com.brningsa.hellsa.myapplication

import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.screens.AddItemScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.ItemsScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.ProfileScreenProducer
import com.brningsa.hellsa.myapplication.ui.screens.SettingsScreenProducer
import com.brningsa.hellsa.navigation.Route
import kotlinx.parcelize.Parcelize

sealed class AppRoute(
    override val screenProducer: () -> AppScreen
) : Route {

    @Parcelize
    data object AddItem : AppRoute(AddItemScreenProducer)

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

val AppRouteTabs = listOf(
    AppRoute.Tab.Items, AppRoute.Tab.Settings, AppRoute.Tab.Profile
)