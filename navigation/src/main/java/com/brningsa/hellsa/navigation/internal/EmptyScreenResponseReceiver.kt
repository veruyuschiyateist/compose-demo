package com.brningsa.hellsa.navigation.internal

import com.brningsa.hellsa.navigation.ScreenResponseReceiver
import kotlin.reflect.KClass

internal object EmptyScreenResponseReceiver : ScreenResponseReceiver {
    override fun <T : Any> get(clazz: KClass<T>): T? = null
}