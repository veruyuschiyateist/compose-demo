package com.brningsa.hellsa.myapplication

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.brningsa.hellsa.navigation.Route
import kotlinx.parcelize.Parcelize

sealed class AppRoute(@StringRes val titleRes: Int = 0) : Route {
    
    @Parcelize
    data object AddItem : AppRoute(R.string.add_item)

    sealed class Tab(
        @StringRes titleRes: Int,
        val icon: ImageVector,
    ) : AppRoute(titleRes) {

        @Parcelize
        data object Items : Tab(R.string.items, Icons.AutoMirrored.Filled.List)

        @Parcelize
        data object Settings : Tab(R.string.settings, Icons.Default.Settings)

        @Parcelize
        data object Profile : Tab(R.string.profile, Icons.Default.AccountBox)
    }
}

val AppRouteTabs = listOf(
    AppRoute.Tab.Items, AppRoute.Tab.Settings, AppRoute.Tab.Profile
)