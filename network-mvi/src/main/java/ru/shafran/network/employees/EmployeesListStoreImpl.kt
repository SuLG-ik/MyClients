package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetAllEmployeesRequest
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

internal class EmployeesListStoreImpl(
    private val storeFactory: StoreFactory,
    private val employeesRepository: EmployeesRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : EmployeesListStore,
    Store<EmployeesListStore.Intent, EmployeesListStore.State, EmployeesListStore.Label> by storeFactory.create(
        name = "EmployeesListStore",
        initialState = EmployeesListStore.State.Loading(emptyList()),
        reducer = ReducerImpl,
        executorFactory = { Executor(employeesRepository, coroutineDispatcher) },
    ) {

    private object ReducerImpl :
        Reducer<EmployeesListStore.State, Result> {

        override fun EmployeesListStore.State.reduce(msg: Result): EmployeesListStore.State {
            return when (msg) {
                is Result.EmployeesLoaded -> EmployeesListStore.State.EmployeesLoaded(msg.employees)
                is Result.Error -> TODO()
                is Result.Loading -> EmployeesListStore.State.Loading()
            }
        }
    }

    private class Executor(
        private val employeesRepository: EmployeesRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<EmployeesListStore.Intent, Nothing, EmployeesListStore.State, Result, EmployeesListStore.Label>(coroutineDispatcher) {

        private var job: Job? = null

        override suspend fun execute(
            intent: EmployeesListStore.Intent,
            getState: () -> EmployeesListStore.State
        ) {
            intent.execute()
        }

        private suspend fun EmployeesListStore.Intent.execute() {
            when (this) {
                is EmployeesListStore.Intent.LoadEmployees -> execute()
            }
        }

        private suspend fun EmployeesListStore.Intent.LoadEmployees.execute() {
            syncDispatch(Result.Loading())
            try {
                val employees = employeesRepository.getAllEmployees(GetAllEmployeesRequest())
                syncDispatch(Result.EmployeesLoaded(employees.employees))
            } catch (e: Exception) {
                syncDispatch(Result.Error(e))
            }
        }

    }

    private sealed class Result {

        data class EmployeesLoaded(
            val employees: List<Employee>,
        ) : Result()

        data class Loading(
            val previousEmployees: List<Employee>? = null,
        ) : Result()

        data class Error(
            val exception: Exception,
        ) : Result()

    }


}