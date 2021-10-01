package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
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

        override suspend fun executeIntent(
            intent: SingleEmployeeStore.Intent,
            getState: () -> SingleEmployeeStore.State,
        ) {
            when (intent) {
                is SingleEmployeeStore.Intent.LoadEmployee -> {
                    dispatch(Result.Loading)
                    val employee = repository.getEmployeeById(intent.id)
                    if (employee != null)
                        dispatch(Result.Loaded(employee))
                    else
                        dispatch(Result.Error("Ошибка"))
                }
                is SingleEmployeeStore.Intent.CreateEmployee -> {
                    dispatch(Result.Loading)
                    val employee = repository.createEmployee(intent.employeeData)
                    dispatch(Result.Loaded(employee))
                    publish(SingleEmployeeStore.Label.OnUpdatedOrCreated)
                }
                is SingleEmployeeStore.Intent.AddImageToEmployee -> {
                    dispatch(Result.Loading)
                    try {
                        val employee =
                            repository.addImageToEmployee(intent.employeeId, intent.image)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        dispatch(Result.Error("Ошибка выполнения"))
                    }
                }
            }
        }

    }


    private object ReducerImpl : Reducer<SingleEmployeeStore.State, Result> {
        override fun SingleEmployeeStore.State.reduce(result: Result): SingleEmployeeStore.State =
            when (result) {
                is Result.Loading -> SingleEmployeeStore.State.Loading
                is Result.Loaded -> SingleEmployeeStore.State.Loaded(
                    employee = result.employee,
                )
                is Result.Error -> SingleEmployeeStore.State.Error(result.message)
                is Result.Hidden -> SingleEmployeeStore.State.Hidden
            }
    }

    sealed class Result {
        object Loading : Result()
        data class Loaded(val employee: Employee) : Result()
        data class Error(val message: String) : Result()
        object Hidden : Result()
    }
}