package com.brningsa.hellsa.myapplication.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.navigation.Screen
import com.brningsa.hellsa.navigation.ScreenEnvironment

@Stable
interface AppScreen : Screen {
    override val environment: AppScreenEnvironment
}

@Stable
class AppScreenEnvironment : ScreenEnvironment {
    var titleRes: Int by mutableIntStateOf(R.string.app_name)
    var icon: ImageVector? by mutableStateOf(null)
    var floatingAction: FloatingAction? by mutableStateOf(null)
}

data class FloatingAction(
    val icon: ImageVector,
    val onClick: () -> Unit
)