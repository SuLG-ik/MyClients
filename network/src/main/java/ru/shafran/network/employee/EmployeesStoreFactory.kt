package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shafran.network.data.employee.Employee

@Suppress("FunctionName")
fun EmployeesStore(
    storeFactory: StoreFactory,
    repository: EmployeesRepository,
): EmployeesListStore {
    return EmployeesStoreFactory(
        storeFactory = storeFactory,
        repository = repository,
    ).create()
}

internal class EmployeesStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: EmployeesRepository
) {

    fun create(): EmployeesListStore =
        object : EmployeesListStore,
            Store<EmployeesListStore.Intent, EmployeesListStore.State, Nothing> by storeFactory.create(
                name = "CounterStore",
                initialState = EmployeesListStore.State(),
                reducer = ReducerImpl,
                executorFactory = {
                    ExecutorImpl(repository = repository)
                }
            ) {}

    private class ExecutorImpl(
        private val repository: EmployeesRepository,
    ) : SuspendExecutor<EmployeesListStore.Intent, Nothing, EmployeesListStore.State, Result, Nothing>(Dispatchers.IO) {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }

        override suspend fun executeIntent(
            intent: EmployeesListStore.Intent,
            getState: () -> EmployeesListStore.State
        ) {
            when (intent) {
                is EmployeesListStore.Intent.LoadEmployees -> {
                    syncDispatch(Result.Value(true))
                    syncDispatch(
                        Result.Value(
                            isLoading = false,
                            employees = repository.getAllEmployees(),
                        )
                    )
                }
            }
        }

    }


    private sealed class Result {
        class Value(
            val isLoading: Boolean = false,
            val employees: List<Employee> = emptyList(),
        ) : Result()
    }

    private object ReducerImpl : Reducer<EmployeesListStore.State, Result> {
        override fun EmployeesListStore.State.reduce(result: Result): EmployeesListStore.State =
            when (result) {
                is Result.Value -> copy(
                    isLoading = result.isLoading,
                    employees = result.employees.sortedBy { it.data.name })
            }
    }

}