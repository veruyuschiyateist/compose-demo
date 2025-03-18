package com.brningsa.hellsa.navigation.links

import android.net.Uri

fun interface DeepLinksHandler {

    fun handleDeepLink(
        uri: Uri,
        inputState: MultiStackState
    ): MultiStackState

    companion object {
        val DEFAULT = DeepLinksHandler { uri, inputState -> inputState }
    }
}