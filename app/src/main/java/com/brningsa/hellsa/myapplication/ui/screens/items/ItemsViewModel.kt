package com.brningsa.hellsa.myapplication.ui.screens.items

import androidx.lifecycle.ViewModel
import com.brningsa.hellsa.myapplication.ItemsRepository
import com.brningsa.hellsa.myapplication.ui.screens.item.ItemScreenArgs
import com.brningsa.hellsa.myapplication.ui.screens.item.ItemScreenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository: ItemsRepository
) : ViewModel() {

    val itemsFlow = repository.getItems()

    fun processResponse(response: ItemScreenResponse) {
        when (response.args) {
            ItemScreenArgs.Add -> {
                repository.addItem(response.newValue)
            }

            is ItemScreenArgs.Edit -> {
                repository.update(response.args.index, response.newValue)
            }
        }
    }
}