package com.brningsa.hellsa.navigation.internal

import android.media.tv.AdResponse
import com.brningsa.hellsa.navigation.Route
import com.brningsa.hellsa.navigation.Router

internal object EmptyRouter : Router {

    override fun launch(route: Route) = Unit

    override fun pop(response: Any?) = Unit

    override fun restart(route: Route) = Unit
}