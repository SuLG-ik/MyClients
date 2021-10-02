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
fun SingleEmployeeStore(
    storeFactory: StoreFactory,
    repository: EmployeesRepository,
): SingleEmployeeStore {
    return SingleEmployeeStoreFactory(
        storeFactory = storeFactory,
        repository = repository,
    ).create()
}


class SingleEmployeeStoreFactory(
    val storeFactory: StoreFactory,
    private val repository: EmployeesRepository,
) {

    fun create(): SingleEmployeeStore {
        return object : SingleEmployeeStore,
            Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> by storeFactory
                .create(
                    name = "SingleEmployeesStore",
                    initialState = SingleEmployeeStore.State.Hidden,
                    reducer = ReducerImpl,
                    executorFactory = { ExecutorImpl(repository) },
                ) {}
    }

    private class ExecutorImpl(
        val repository: EmployeesRepository,
    ) : SuspendExecutor<SingleEmployeeStore.Intent, Nothing, SingleEmployeeStore.State, Result, SingleEmployeeStore.Label>() {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }


        override suspend fun executeIntent(
            intent: SingleEmployeeStore.Intent,
            getState: () -> SingleEmployeeStore.State,
        ) {
            try {
                when (intent) {
                    is SingleEmployeeStore.Intent.LoadEmployee -> {
                        dispatch(Result.Loading)
                        val employee = repository.getEmployeeById(intent.id)
                        syncDispatch(Result.Loaded(employee))
                    }
                    is SingleEmployeeStore.Intent.CreateEmployee -> {
                        dispatch(Result.Loading)
                        val employee = repository.createEmployee(intent.employeeData)
                        dispatch(Result.Hidden)
                        publish(SingleEmployeeStore.Label.OnCreated(employee))
                    }
                    is SingleEmployeeStore.Intent.AddImageToEmployee -> {
                        dispatch(Result.Loading)
                        val employee =
                            repository.addImageToEmployee(intent.employeeId, intent.image)
                        dispatch(Result.Loaded(employee))
                        publish(SingleEmployeeStore.Label.OnUpdated)
                    }
                    is SingleEmployeeStore.Intent.DeleteEmployee -> {
                        dispatch(Result.Loading)
                        repository.deleteEmployeeById(intent.employeeId)
                        dispatch(Result.Hidden)
                        publish(SingleEmployeeStore.Label.OnDeleted)
                    }
                    is SingleEmployeeStore.Intent.EditEmployee -> {
                        dispatch(Result.Loading)
                        val employee =
                            repository.updateEmployeeById(intent.employeeId, intent.employeeData)
                        dispatch(Result.Loaded(employee))
                        publish(SingleEmployeeStore.Label.OnUpdated)
                    }
                }
            } catch (e: Exception) {
                syncDispatch(Result.Error(e))
            }
        }

    }


    private object ReducerImpl : Reducer<SingleEmployeeStore.State, Result> {
        override fun SingleEmployeeStore.State.reduce(result: Result): SingleEmployeeStore.State =
            when (result) {
                is Result.Loaded -> {
                    SingleEmployeeStore.State.EmployeeLoaded(result.employee)
                }
                is Result.Loading -> SingleEmployeeStore.State.Loading
                is Result.Error -> {
                    when (val exception = result.exception) {
                        is ResponseException -> {
                            when (exception.response.status) {
                                HttpStatusCode.InternalServerError -> {
                                    SingleEmployeeStore.State.Error.InternalServerError
                                }
                                HttpStatusCode.NotFound -> {
                                    SingleEmployeeStore.State.Error.NotFoundException
                                }
                                else -> {
                                    exception.printStackTrace()
                                    SingleEmployeeStore.State.Error.UnknownError
                                }
                            }
                        }
                        else -> {
                            exception.printStackTrace()
                            SingleEmployeeStore.State.Error.UnknownError
                        }
                    }
                }
                Result.Hidden -> SingleEmployeeStore.State.Hidden
            }
    }

    sealed class Result {
        object Loading : Result()
        data class Loaded(val employee: Employee) : Result()
        data class Error(val exception: Exception) : Result()
        object Hidden : Result()
    }
}