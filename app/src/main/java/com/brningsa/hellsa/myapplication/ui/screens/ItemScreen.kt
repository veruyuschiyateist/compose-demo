package com.brningsa.hellsa.myapplication.ui.screens

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brningsa.hellsa.myapplication.ItemsRepository
import com.brningsa.hellsa.myapplication.R
import com.brningsa.hellsa.myapplication.ui.AppScreen
import com.brningsa.hellsa.myapplication.ui.AppScreenEnvironment
import com.brningsa.hellsa.navigation.LocalRouter
import kotlinx.parcelize.Parcelize

fun itemScreenProducer(args: ItemScreenArgs): () -> ItemScreen = { ItemScreen(args) }

sealed class ItemScreenArgs : Parcelable {

    @Parcelize
    data object Add : ItemScreenArgs()

    @Parcelize
    data class Edit(val index: Int) : ItemScreenArgs()
}

data class ItemScreenResponse(
    val args: ItemScreenArgs,
    val newValue: String
)

class ItemScreen(
    private val args: ItemScreenArgs
) : AppScreen {

    override val environment = AppScreenEnvironment().apply {
        titleRes = if (args is ItemScreenArgs.Add) {
            R.string.add_item
        } else {
            R.string.edit_item
        }
    }

    @Composable
    override fun Content() {
        val itemsRepository = ItemsRepository.get()
        val router = LocalRouter.current

        ItemContent(
            initialValue = if (args is ItemScreenArgs.Edit) {
                remember { itemsRepository.getItems().value[args.index] }
            } else {
                ""
            },
            isAddMode = args is ItemScreenArgs.Add,
            onSubmitNewItem = { newValue ->
                router.pop(ItemScreenResponse(args, newValue))
            }
        )
    }

}

@Composable
fun ItemContent(
    initialValue: String = "",
    isAddMode: Boolean = false,
    onSubmitNewItem: (String) -> Unit
) {
    var currentItemValue by rememberSaveable {
        mutableStateOf(initialValue)
    }
    val isAddEnabled by remember {
        derivedStateOf { currentItemValue.isNotEmpty() }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = currentItemValue,
            onValueChange = {
                currentItemValue = it
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_a_new_value)
                )
            },
            singleLine = true,
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(
            enabled = isAddEnabled,
            onClick = {
                onSubmitNewItem(currentItemValue)
            }
        ) {
            val buttonText = if (isAddMode) {
                stringResource(R.string.add_new_item)
            } else {
                stringResource(R.string.edit_item)
            }
            Text(text = buttonText)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemPreview() {
    ItemContent(onSubmitNewItem = {})
}