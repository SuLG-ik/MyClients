package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class SingleEmployeeStoreImpl(
    storeFactory: StoreFactory,
    employeesRepository: EmployeesRepository,
) :
    SingleEmployeeStore,
    Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> by storeFactory.create(
        name = "SingleEmployeeStore",
        initialState = SingleEmployeeStore.State.Empty,
        reducer = ReducerImpl,
        executorFactory = { Executor(employeesRepository) },
    ) {

    private object ReducerImpl :
        Reducer<SingleEmployeeStore.State, Message> {

        override fun SingleEmployeeStore.State.reduce(msg: Message): SingleEmployeeStore.State {
            return when (msg) {
                is Message.EmployeeLoaded -> SingleEmployeeStore.State.EmployeeLoaded(msg.employee)
                is Message.Error -> SingleEmployeeStore.State.Error.Unknown
                is Message.Loading -> SingleEmployeeStore.State.Loading()
            }
        }
    }

    private class Executor(
        private val employeesRepository: EmployeesRepository,
    ) :
        SafeCancelableSyncCoroutineExecutor<SingleEmployeeStore.Intent, Nothing, SingleEmployeeStore.State, Message, SingleEmployeeStore.Label>() {
        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: SingleEmployeeStore.Intent,
            getState: () -> SingleEmployeeStore.State,
        ) {
            when (intent) {
                is SingleEmployeeStore.Intent.LoadEmployee ->
                    intent.execute()
                is SingleEmployeeStore.Intent.LoadEmployeeWithId ->
                    intent.execute()
            }
        }

        private suspend fun SingleEmployeeStore.Intent.LoadEmployee.execute() {
            syncDispatch(Message.EmployeeLoaded(employee))
        }

        private suspend fun SingleEmployeeStore.Intent.LoadEmployeeWithId.execute() {
            syncDispatch(Message.Loading())
            val response = employeesRepository.getEmployeeById(request)
            syncDispatch(Message.EmployeeLoaded(response.employee))
        }

    }

    private sealed class Message {
        data class EmployeeLoaded(val employee: Employee) : Message()
        class Loading : Message()
        class Error(val exception: Exception) : Message()
    }


}