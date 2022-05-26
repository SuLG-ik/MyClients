package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetAllEmployeesRequest
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class EmployeesListStoreImpl(
    private val storeFactory: StoreFactory,
    private val employeesRepository: EmployeesRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : EmployeesListStore,
    Store<EmployeesListStore.Intent, EmployeesListStore.State, EmployeesListStore.Label> by storeFactory.create(
        name = "EmployeesListStore",
        initialState = EmployeesListStore.State.Empty,
        reducer = ReducerImpl,
        executorFactory = { Executor(employeesRepository, coroutineDispatcher) },
    ) {

    private object ReducerImpl :
        Reducer<EmployeesListStore.State, Message> {

        override fun EmployeesListStore.State.reduce(msg: Message): EmployeesListStore.State {
            return when (msg) {
                is Message.EmployeesLoaded -> EmployeesListStore.State.EmployeesLoaded(msg.employees)
                is Message.Error -> msg.reduce()
                is Message.Loading -> EmployeesListStore.State.Loading()
            }
        }

        private fun Message.Error.reduce(): EmployeesListStore.State {
            return when (exception) {
                else -> {
                    Napier.e({ "Unknown exception" }, exception)
                    EmployeesListStore.State.Error.Unknown
                }
            }
        }


    }

    private class Executor(
        private val employeesRepository: EmployeesRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : SafeCancelableSyncCoroutineExecutor<EmployeesListStore.Intent, Nothing, EmployeesListStore.State, Message, EmployeesListStore.Label>(
        coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: EmployeesListStore.Intent,
            getState: () -> EmployeesListStore.State,
        ) {
            intent.execute()
        }

        private suspend fun EmployeesListStore.Intent.execute() {
            when (this) {
                is EmployeesListStore.Intent.LoadEmployees -> execute()
            }
        }

        private suspend fun EmployeesListStore.Intent.LoadEmployees.execute() {
            syncDispatch(Message.Loading())
            try {
                val employees =
                    employeesRepository.getAllEmployees(GetAllEmployeesRequest(companyId = companyId))
                syncDispatch(Message.EmployeesLoaded(employees.employees))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e))
            }
        }

    }

    private sealed class Message {

        data class EmployeesLoaded(
            val employees: List<Employee>,
        ) : Message()

        data class Loading(
            val previousEmployees: List<Employee>? = null,
        ) : Message()

        data class Error(
            val exception: Exception,
        ) : Message()

    }


}