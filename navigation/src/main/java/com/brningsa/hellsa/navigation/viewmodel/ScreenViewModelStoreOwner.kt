package com.brningsa.hellsa.navigation.viewmodel

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

internal class ScreenViewModelStoreOwner(
    screenViewModelStoreProvider: ScreenViewModelStoreProvider,
    uuid: String
) : ViewModelStoreOwner {

    override val viewModelStore: ViewModelStore =
        screenViewModelStoreProvider.getStore(uuid)

}