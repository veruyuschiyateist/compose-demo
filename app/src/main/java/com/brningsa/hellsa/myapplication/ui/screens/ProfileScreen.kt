package com.brningsa.hellsa.myapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.AppScreenEnvironment

val ProfileScreenProducer = { ProfileScreen() }

class ProfileScreen : AppScreen {

    override val environment = AppScreenEnvironment().apply {
        titleRes = R.string.profile
        icon = Icons.Default.AccountBox
    }

    @Composable
    override fun Content() {
        Text(text = "Profile Screen", fontSize = 28.sp)
    }

}
