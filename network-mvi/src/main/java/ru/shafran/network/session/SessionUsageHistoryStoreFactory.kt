package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor

internal class SessionUsageHistoryStoreFactory(private val storeFactory: StoreFactory) {

    fun build(): SessionUsageHistoryStore = object : SessionUsageHistoryStore,
        Store<SessionUsageHistoryStore.Intent, SessionUsageHistoryStore.State, SessionUsageHistoryStore.Label> by storeFactory.create(
            name = "ServicesListStore",
            initialState = SessionUsageHistoryStore.State.Loading(emptyList()),
            reducer = ReducerImpl,
            executorFactory = { Executor() },
        ) {}

    private object ReducerImpl :
        Reducer<SessionUsageHistoryStore.State, Result> {

        override fun SessionUsageHistoryStore.State.reduce(result: Result): SessionUsageHistoryStore.State {
            return this
        }
    }

    private class Executor() :
        SuspendExecutor<SessionUsageHistoryStore.Intent, Nothing, SessionUsageHistoryStore.State, Result, SessionUsageHistoryStore.Label>()

    private sealed class Result {
        class B() : Result()
    }


}