package ru.shafran.network.utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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
fun <Intent: Any, State: Any, Label: Any> Store<Intent, State, Label>.reduceStates(
    lifecycleOwner: LifecycleOwner,
    reducer: (State) -> Unit,
): Store<Intent, State, Label> {
    val scope = lifecycleOwner.createCoroutineScope()
    lifecycleOwner.lifecycle.doOnCreate {
        states.onEach(reducer).launchIn(scope)
    }
    return this
}


@OptIn(ExperimentalCoroutinesApi::class)
fun <Intent: Any, State: Any, Label: Any> Store<Intent, State, Label>.reduceLabels(
    lifecycleOwner: LifecycleOwner,
    reducer: (Label) -> Unit,
): Store<Intent, State, Label> {
    val scope = lifecycleOwner.createCoroutineScope()
    lifecycleOwner.lifecycle.doOnCreate {
        labels.onEach(reducer).launchIn(scope)
    }
    return this
}

fun <Intent: Any, State: Any, Label: Any> Store<Intent, State, Label>.acceptOnCreate(
    lifecycleOwner: LifecycleOwner,
    intent: Intent,
): Store<Intent, State, Label> {
    lifecycleOwner.lifecycle.doOnCreate {
        accept(intent)
    }
    return this
}
