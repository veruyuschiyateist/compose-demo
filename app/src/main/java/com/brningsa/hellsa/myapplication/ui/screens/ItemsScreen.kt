package com.brningsa.hellsa.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brningsa.hellsa.myapplication.AppRoute
import com.brningsa.hellsa.myapplication.ItemsRepository
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.AppScreenEnvironment
import com.brningsa.hellsa.myapplication.ui.FloatingAction
import com.brningsa.hellsa.navigation.LocalRouter
import com.brningsa.hellsa.navigation.ResponseListener
import com.brningsa.hellsa.navigation.Router

val ItemsScreenProducer = { ItemsScreen() }

class ItemsScreen : AppScreen {

    private var router: Router? = null

    override val environment = AppScreenEnvironment().apply {
        titleRes = R.string.items
        icon = Icons.AutoMirrored.Filled.List
        floatingAction = FloatingAction(
            icon = Icons.Default.Add,
            onClick = {
                router?.launch(route = AppRoute.Item(ItemScreenArgs.Add))
            }
        )
    }

    @Composable
    override fun Content() {
        router = LocalRouter.current

        val itemsRepository = ItemsRepository.get()
        val items by itemsRepository.getItems().collectAsStateWithLifecycle()

        val isEmpty by remember {
            derivedStateOf { items.isEmpty() }
        }
        ResponseListener<ItemScreenResponse> { response ->
            if (response.args is ItemScreenArgs.Edit) {
                itemsRepository.update(response.args.index, response.newValue)
            } else {
                itemsRepository.addItem(response.newValue)
            }
        }
        ItemsContent(
            isItemsEmpty = isEmpty,
            items = { items },
            onItemClicked = { index ->
                router?.launch(route = AppRoute.Item(ItemScreenArgs.Edit(index)))
            }
        )
    }

}

@Composable
fun ItemsContent(
    isItemsEmpty: Boolean,
    items: () -> List<String>,
    onItemClicked: (Int) -> Unit
) {
    if (isItemsEmpty) {
        Text(
            text = "No Items",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    } else {
        LazyColumn {
            val itemList = items()

            items(itemList.size) { index ->
                Text(
                    text = itemList[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClicked.invoke(index)
                        }
                        .padding(all = 8.dp)
                )
            }
        }
    }
}