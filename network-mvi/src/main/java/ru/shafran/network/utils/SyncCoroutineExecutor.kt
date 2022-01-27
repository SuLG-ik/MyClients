package ru.shafran.network.utils

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
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