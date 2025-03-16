package com.brningsa.hellsa.myapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.AppScreenEnvironment

val SettingsScreenProducer = { SettingsScreen() }

class SettingsScreen : AppScreen {
    override val environment = AppScreenEnvironment().apply {
        titleRes = R.string.settings
        icon = Icons.Default.Settings
    }

    @Composable
    override fun Content() {
        Text(text = "Settings Screen", fontSize = 28.sp)
    }

}
