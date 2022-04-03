package ru.shafran.network.utils

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class SyncCoroutineExecutor<in Intent : Any, in Action : Any, in State : Any, Message : Any, Label : Any> :
    CoroutineExecutor<Intent, Action, State, Message, Label>() {

    protected suspend fun syncDispatch(result: Message) {
        withContext(Dispatchers.Main) { dispatch(result) }
    }

    protected suspend fun syncPublish(label: Label) {
        withContext(Dispatchers.Main) { publish(label) }
    }

}

abstract class SafeCancelableSyncCoroutineExecutor<in Intent : Any, in Action : Any, in State : Any, Message : Any, Label : Any>(
    defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CancelableSyncCoroutineExecutor<Intent, Action, State, Message, Label>(defaultDispatcher) {

    abstract suspend fun buildErrorMessage(exception: Exception): Message

    final override suspend fun execute(intent: Intent, getState: () -> State) {
        try {
            safeExecute(intent, getState)
        } catch (exception: Exception) {
            syncDispatch(buildErrorMessage(exception))
        }
    }


    abstract suspend fun safeExecute(intent: Intent, getState: () -> State)
}

abstract class CancelableSyncCoroutineExecutor<in Intent : Any, in Action : Any, in State : Any, Message : Any, Label : Any>(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    CoroutineExecutor<Intent, Action, State, Message, Label>() {

    private val supervisorJob = SupervisorJob()

    private var currentJob: Job? = null

    protected suspend fun syncDispatch(result: Message) {
        withContext(Dispatchers.Main) { dispatch(result) }
    }

    protected suspend fun syncPublish(label: Label) {
        withContext(Dispatchers.Main) { publish(label) }
    }

    final override fun executeAction(action: Action, getState: () -> State) {
        super.executeAction(action, getState)
    }

    final override fun executeIntent(intent: Intent, getState: () -> State) {
        currentJob?.cancel()
        currentJob = scope.launch(defaultDispatcher + supervisorJob) { execute(intent, getState) }
    }

    protected abstract suspend fun execute(intent: Intent, getState: () -> State)

}