package com.brningsa.hellsa.myapplication

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.brningsa.hellsa.myapplication.di.injectViewModel
import com.brningsa.hellsa.myapplication.ui.AppDeepLinkHandler
import com.brningsa.hellsa.myapplication.ui.AppScreenEnvironment
import com.brningsa.hellsa.myapplication.ui.scaffold.AppFloatingActionButton
import com.brningsa.hellsa.myapplication.ui.theme.MyApplicationTheme
import com.brningsa.hellsa.navigation.NavigationHost
import com.brningsa.hellsa.navigation.rememberNavigation

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun AppScaffold() {

    val viewModel = injectViewModel<MainViewModel>()
    val navigation = rememberNavigation(AppRouteTabs, deepLinksHandler = AppDeepLinkHandler)
    val (router, navigationState) = navigation
    val environment = navigationState.currentScreen.environment as AppScreenEnvironment

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            environment.titleRes
                        ),
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!navigationState.isRoot) router.pop()
                        }
                    ) {
                        Icon(
                            imageVector = if (navigationState.isRoot)
                                Icons.Default.Menu
                            else
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.main_menu)
                        )
                    }
                },
                actions = {
                    var showPopupMenu by remember { mutableStateOf(false) }
                    val context = LocalContext.current

                    IconButton(
                        onClick = { showPopupMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_actions),
                        )
                    }
                    DropdownMenu(
                        expanded = showPopupMenu,
                        onDismissRequest = { showPopupMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.about))
                            },
                            onClick = {
                                Toast.makeText(context, "Scaffold App", Toast.LENGTH_SHORT).show()
                                showPopupMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.clear))
                            },
                            onClick = {
                                viewModel.clear()
                                showPopupMenu = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                floatingAction = environment.floatingAction
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            NavigationBar {
                AppRouteTabs.forEachIndexed { index, tab ->
                    val environment = remember(tab) {
                        tab.screenProducer().environment
                    }
                    val icon = environment.icon
                    if (icon != null) {
                        NavigationBarItem(
                            selected = navigationState.currentStackIndex == index,
                            label = {
                                Text(text = stringResource(environment.titleRes))
                            },
                            onClick = {
                                router.switchStack(index)
                            },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = stringResource(environment.titleRes)
                                )
                            }
                        )
                    }

                }
            }
        },
    ) { paddingValues ->
        NavigationHost(
            navigation = navigation,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppScreenPreview() {
    MyApplicationTheme(darkTheme = true) {
        AppScaffold()
    }
}