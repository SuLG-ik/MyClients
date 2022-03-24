package ru.shafran.common.utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.consume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.network.utils.cancelInLifecycle
import ru.shafran.network.utils.createCoroutineScope
import kotlin.coroutines.CoroutineContext

internal fun CoroutineScope.cancelInLifecycle(lifecycle: Lifecycle): CoroutineScope {
    return cancelInLifecycle(lifecycle)
}

internal fun LifecycleOwner.createCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope {
    return createCoroutineScope(context)
}

internal inline fun <reified T : Parcelable> StateKeeper.consumeMutableStateFlow(key: String): MutableStateFlow<T?> {
    val state = MutableStateFlow(consume<T>(key))
    register<Parcelable>(key) { (state.value ?: ParcelableContainer(null)) }
    return state
}