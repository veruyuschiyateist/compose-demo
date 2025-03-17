package com.brningsa.hellsa.navigation.internal

import com.brningsa.hellsa.navigation.ScreenResponseReceiver
import kotlin.reflect.KClass

class ScreenResponseReceiverBus : ScreenResponseReceiver {

    private var response: Any? = null

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: KClass<T>): T? {
        val response = this.response
        if (response != null && clazz.isInstance(response)) {
            this.response = null
            return response as T
        }

        return null
    }

    fun send(response: Any) {
        this.response = response
    }

    fun cleanUp() {
        this.response = null
    }
}