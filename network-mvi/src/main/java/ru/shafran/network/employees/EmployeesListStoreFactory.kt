package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.shafran.network.utils.SyncCoroutineExecutor

internal class EmployeesListStoreFactory(private val storeFactory: StoreFactory) {

    fun build(): EmployeesListStore = object : EmployeesListStore,
        Store<EmployeesListStore.Intent, EmployeesListStore.State, EmployeesListStore.Label> by storeFactory.create(
            name = "EmployeesListStore",
            initialState = EmployeesListStore.State.Loading(emptyList()),
            reducer = ReducerImpl,
            executorFactory = { Executor() },
        ) {}

    private object ReducerImpl :
        Reducer<EmployeesListStore.State, Result> {

        override fun EmployeesListStore.State.reduce(result: Result): EmployeesListStore.State {
            return this
        }
    }

    private class Executor() :
        SyncCoroutineExecutor<EmployeesListStore.Intent, Nothing, EmployeesListStore.State, Result, EmployeesListStore.Label>()

    private sealed class Result {
        class B() : Result()
    }


}