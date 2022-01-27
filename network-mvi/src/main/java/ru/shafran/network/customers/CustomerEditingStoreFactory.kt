package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.shafran.network.utils.SyncCoroutineExecutor

internal class CustomerEditingStoreFactory(
    private val factory: StoreFactory,
) {

    fun build(): CustomerEditingStore =
        object : CustomerEditingStore,
            Store<CustomerEditingStore.Intent, CustomerEditingStore.State, CustomerEditingStore.Label> by factory.create(
                name = "CustomerEditingStore",
                initialState = CustomerEditingStore.State.Loading(),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {

        }

    private object ReducerImpl : Reducer<CustomerEditingStore.State, Message> {
        override fun CustomerEditingStore.State.reduce(msg: Message): CustomerEditingStore.State {
            TODO("Not yet implemented")
        }
    }

    private class Executor :
        SyncCoroutineExecutor<CustomerEditingStore.Intent, Nothing, CustomerEditingStore.State, Message, CustomerEditingStore.Label>() {}

    private sealed class Message

}