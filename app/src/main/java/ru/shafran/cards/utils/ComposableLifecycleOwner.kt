package ru.shafran.cards.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

@Composable
fun rememberLifecycleOwner(): LifecycleOwner {
    val owner = LocalLifecycleOwner.current

    val composableOwner = remember { ComposableLifecycleOwner(owner) }
    DisposableEffect(key1 = Unit) {
        onDispose(composableOwner::onDispose)
    }
    return composableOwner
}

private class ComposableLifecycleOwner(lifecycleOwner: LifecycleOwner) : LifecycleOwner {

    private val registry = LifecycleRegistry(this)
    var isDisposed = false

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
            if (!isDisposed)
                registry.handleLifecycleEvent(event)
        })
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    fun onDispose() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        isDisposed = true
    }
}
