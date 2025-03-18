package com.brningsa.hellsa.myapplication.ui

import android.net.Uri
import com.brningsa.hellsa.myapplication.AppRoute
import com.brningsa.hellsa.myapplication.ui.screens.ItemScreenArgs
import com.brningsa.hellsa.navigation.links.DeepLinksHandler
import com.brningsa.hellsa.navigation.links.MultiStackState

object AppDeepLinkHandler : DeepLinksHandler {

    override fun handleDeepLink(
        uri: Uri, inputState: MultiStackState
    ): MultiStackState {
        var outputState = inputState
        if (uri.scheme == "nav") {
            if (uri.host == "settings") {
                outputState = inputState.copy(activeStackIndex = 2)
            } else if (uri.host == "items") {
                val itemIndex = uri.pathSegments?.firstOrNull()?.toIntOrNull()
                if (itemIndex != null) {
                    val editItemRoute = AppRoute.Item(ItemScreenArgs.Edit(itemIndex))
                    outputState = inputState.withNewRoute(stackIndex = 0, editItemRoute)
                }
            }
        }

        return outputState
    }
}