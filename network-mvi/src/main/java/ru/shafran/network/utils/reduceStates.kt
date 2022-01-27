package ru.shafran.network.utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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


@OptIn(ExperimentalCoroutinesApi::class)
fun <State : Any> Store<*, State, *>.reduceStates(lifecycleOwner: LifecycleOwner, reducer: (State) -> Unit): Store<*, State, *> {
    val scope = lifecycleOwner.createCoroutineScope()
    lifecycleOwner.lifecycle.doOnCreate {
        states.onEach(reducer).launchIn(scope)
    }
    return this
}


@OptIn(ExperimentalCoroutinesApi::class)
fun <Label : Any> Store<*, *, Label>.reduceLabels(lifecycleOwner: LifecycleOwner, reducer: (Label) -> Unit): Store<*, *, Label> {
    val scope = lifecycleOwner.createCoroutineScope()
    lifecycleOwner.lifecycle.doOnCreate {
        labels.onEach(reducer).launchIn(scope)
    }
    return this
}