package com.brningsa.hellsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface Screen {

    val environment: ScreenEnvironment

    @Composable
    fun Content()
}