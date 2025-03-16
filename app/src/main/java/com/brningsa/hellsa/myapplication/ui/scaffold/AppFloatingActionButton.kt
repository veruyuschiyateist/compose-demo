package com.brningsa.hellsa.myapplication.ui.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brningsa.hellsa.myapplication.AppRoute
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.myapplication.ui.FloatingAction
import com.brningsa.hellsa.navigation.Route

@Composable
fun AppFloatingActionButton(
    floatingAction: FloatingAction?,
    modifier: Modifier = Modifier
) {
    if (floatingAction != null) {
        FloatingActionButton(
            modifier = modifier,
            onClick = floatingAction.onClick
        ) {
            Icon(
                imageVector = floatingAction.icon,
                contentDescription = stringResource(R.string.add_new_item)
            )
        }
    }
}