package ru.shafran.cards.utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.cancelInLifecycle(lifecycle: Lifecycle): CoroutineScope {
    if (lifecycle.state == Lifecycle.State.DESTROYED)
        cancel()
    else
        lifecycle.doOnDestroy(this::cancel)
    return this
}

fun LifecycleOwner.createCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope {
    return CoroutineScope(context).cancelInLifecycle(lifecycle)
}