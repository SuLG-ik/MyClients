package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor

internal class SingleEmployeeStoreFactory(private val storeFactory: StoreFactory) {

    fun build(): SingleEmployeeStore = object : SingleEmployeeStore,
        Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> by storeFactory.create(
            name = "SingleEmployeeStore",
            initialState = SingleEmployeeStore.State.Loading(emptyList()),
            reducer = ReducerImpl,
            executorFactory = { Executor() },
        ) {}

    private object ReducerImpl :
        Reducer<SingleEmployeeStore.State, Message> {

        override fun SingleEmployeeStore.State.reduce(msg: Message): SingleEmployeeStore.State {
            return this
        }
    }

    private class Executor() :
        SuspendExecutor<SingleEmployeeStore.Intent, Nothing, SingleEmployeeStore.State, Message, SingleEmployeeStore.Label>()

    private sealed class Message {
        class B() : Message()
    }


}