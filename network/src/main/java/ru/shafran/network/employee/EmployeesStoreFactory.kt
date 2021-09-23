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
): EmployeesStore {
    return EmployeesStoreFactory(
        storeFactory = storeFactory,
        repository = repository,
    ).create()
}

internal class EmployeesStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: EmployeesRepository
) {

    fun create(): EmployeesStore =
        object : EmployeesStore,
            Store<EmployeesStore.Intent, EmployeesStore.State, Nothing> by storeFactory.create(
                name = "CounterStore",
                initialState = EmployeesStore.State(),
                reducer = ReducerImpl,
                executorFactory = {
                    ExecutorImpl(repository = repository)
                }
            ) {}

    private class ExecutorImpl(
        private val repository: EmployeesRepository,
    ) : SuspendExecutor<EmployeesStore.Intent, Nothing, EmployeesStore.State, Result, Nothing>(Dispatchers.IO) {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }

        override suspend fun executeIntent(
            intent: EmployeesStore.Intent,
            getState: () -> EmployeesStore.State
        ) {
            return when (intent) {
                is EmployeesStore.Intent.LoadEmployees -> {
                    syncDispatch(Result.Value(true))
                    syncDispatch(
                        Result.Value(
                            isLoading = false,
                            employees = repository.getAllEmployees(),
                        )
                    )
                }
                is EmployeesStore.Intent.CreateEmployee -> {
                    val state = getState()
                    syncDispatch(Result.Value(true))
                    val employee = repository.createEmployee(intent.employeeData)
                    syncDispatch(
                        Result.Value(
                            isLoading = false,
                            employees = state.employees + employee,
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

    private object ReducerImpl : Reducer<EmployeesStore.State, Result> {
        override fun EmployeesStore.State.reduce(result: Result): EmployeesStore.State =
            when (result) {
                is Result.Value -> copy(
                    isLoading = isLoading,
                    employees = employees.sortedBy { it.data.name })
            }
    }

}