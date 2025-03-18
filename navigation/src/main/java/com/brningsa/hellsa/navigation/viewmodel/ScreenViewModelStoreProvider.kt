package com.brningsa.hellsa.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

internal class ScreenViewModelStoreProvider : ViewModel() {

    private val stores = mutableMapOf<String, ViewModelStore>()

    fun removeStore(uuid: String) {
        val store = stores.remove(uuid)
        store?.clear()
    }

    fun getStore(uuid: String): ViewModelStore {
        return stores.computeIfAbsent(uuid) { ViewModelStore() }
    }

    override fun onCleared() {
        super.onCleared()
        stores.values.forEach { it.clear() }
        stores.clear()
    }
}