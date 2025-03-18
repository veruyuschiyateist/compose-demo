package com.brningsa.hellsa.myapplication

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

interface ItemsRepository {

    fun getItems(): StateFlow<List<String>>

    fun addItem(item: String)

    fun update(index: Int, newValue: String)

    fun clear()

//    old way
//    companion object {
//        fun get(): ItemsRepository = ItemsRepositoryImpl
//    }
}

@Singleton
class ItemsRepositoryImpl @Inject constructor() : ItemsRepository {

    private val items = MutableStateFlow(generateFakeItems())

    override fun getItems(): StateFlow<List<String>> {
        return items
    }

    override fun addItem(item: String) {
        items.update { it + item }
    }

    override fun update(index: Int, newValue: String) {
        items.update {
            it.toMutableList().apply { set(index, newValue) }
        }
    }


    override fun clear() {
        items.update { emptyList() }
    }

    private fun generateFakeItems() = List(10) {
        "Item #${it + 1}"
    }
}