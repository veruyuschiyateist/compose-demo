package com.brningsa.hellsa.myapplication

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ItemsRepository {

    fun getItems(): StateFlow<List<String>>

    fun addItem(item: String)

    fun clear()

    companion object {
        fun get(): ItemsRepository = ItemsRepositoryImpl
    }
}

object ItemsRepositoryImpl : ItemsRepository {

    private val items = MutableStateFlow(generateFakeItems())

    override fun getItems(): StateFlow<List<String>> {
        return items
    }

    override fun addItem(item: String) {
        items.update { it + item }
    }

    override fun clear() {
        items.update { emptyList() }
    }

    private fun generateFakeItems() = List(10) {
        "Item #${it + 1}"
    }
}