package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import io.ktor.client.features.*
import io.ktor.http.*
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
    private val repository: EmployeesRepository,
) {

    fun create(): EmployeesListStore =
        object : EmployeesListStore,
            Store<EmployeesListStore.Intent, EmployeesListStore.State, Nothing> by storeFactory.create(
                name = "CounterStore",
                initialState = EmployeesListStore.State.Loading,
                reducer = ReducerImpl,
                executorFactory = {
                    ExecutorImpl(repository = repository)
                }
            ) {}

    private class ExecutorImpl(
        private val repository: EmployeesRepository,
    ) : SuspendExecutor<EmployeesListStore.Intent, Nothing, EmployeesListStore.State, Result, Nothing>(
        Dispatchers.IO) {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }

        override suspend fun executeIntent(
            intent: EmployeesListStore.Intent,
            getState: () -> EmployeesListStore.State,
        ) {
            when (intent) {
                is EmployeesListStore.Intent.LoadEmployees -> {
                    syncDispatch(Result.Loading)
                    syncDispatch(
                        try {
                            Result.EmployeesLoaded(
                                employees = repository.getAllEmployees(),
                            )
                        } catch (e: Exception) {
                            Result.Error(e)
                        }
                    )
                }
            }
        }

    }


    private sealed class Result {
        object Loading : Result()
        class EmployeesLoaded(
            val employees: List<Employee> = emptyList(),
        ) : Result()

        data class Error(val exception: Exception) : Result()
    }

    private object ReducerImpl : Reducer<EmployeesListStore.State, Result> {
        override fun EmployeesListStore.State.reduce(result: Result): EmployeesListStore.State =
            when (result) {
                is Result.EmployeesLoaded -> {
                    EmployeesListStore.State.EmployeesLoaded(
                        employees = result.employees.sortedBy { it.data.name }
                    )
                }
                is Result.Loading -> EmployeesListStore.State.Loading
                is Result.Error -> {
                    when (val exception = result.exception) {
                        is ResponseException -> {
                            when (exception.response.status) {
                                HttpStatusCode.InternalServerError -> {
                                    EmployeesListStore.State.Error.InternalServerError
                                }
                                else -> {
                                    exception.printStackTrace()
                                    EmployeesListStore.State.Error.UnknownError
                                }
                            }
                        }
                        else -> {
                            exception.printStackTrace()
                            EmployeesListStore.State.Error.UnknownError
                        }
                    }
                }
            }

    }
}