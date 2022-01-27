package ru.shafran.common.utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.shafran.network.utils.cancelInLifecycle
import ru.shafran.network.utils.createCoroutineScope
import kotlin.coroutines.CoroutineContext

internal fun CoroutineScope.cancelInLifecycle(lifecycle: Lifecycle): CoroutineScope {
    return cancelInLifecycle(lifecycle)
}

internal fun LifecycleOwner.createCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope {
    return createCoroutineScope(context)
}

