package ru.shafran.common.utils

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

fun <T : Any> Value<T>.stateIn(scope: CoroutineScope): StateFlow<T> {
    val flow = MutableSharedFlow<T>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val subscription: (T) -> Unit = { flow.tryEmit(it) }
    flow.subscriptionCount.onEach {
        if (it > 0) {
            subscribe(subscription)
        } else {
            unsubscribe(subscription)
        }
    }.launchIn(scope)
    return flow.stateIn(scope, SharingStarted.Lazily, value)
}