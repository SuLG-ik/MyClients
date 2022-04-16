package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeRequestData
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

class CreateEmployeeStoreImpl(
    storeFactory: StoreFactory,
    employeesRepository: EmployeesRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CreateEmployeeStore,
    Store<CreateEmployeeStore.Intent, CreateEmployeeStore.State, CreateEmployeeStore.Label> by storeFactory.create(
        name = "CreateEmployeeStore",
        initialState = CreateEmployeeStore.State.CreateEmployee(),
        executorFactory = {
            Executor(
                employeesRepository = employeesRepository,
                coroutineDispatcher = coroutineDispatcher,
            )
        },
        reducer = ReducerImpl
    ) {


    private class Executor(
        private val employeesRepository: EmployeesRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<CreateEmployeeStore.Intent, Nothing, CreateEmployeeStore.State, Message, CreateEmployeeStore.Label>(
            coroutineDispatcher) {

        override suspend fun execute(
            intent: CreateEmployeeStore.Intent,
            getState: () -> CreateEmployeeStore.State,
        ) {
            when (intent) {
                is CreateEmployeeStore.Intent.CreateEmployee ->
                    intent.execute()
                is CreateEmployeeStore.Intent.LoadDetails ->
                    syncDispatch(Message.CreateEmployee(intent.request))
            }
        }

        private suspend fun CreateEmployeeStore.Intent.CreateEmployee.execute() {
            syncDispatch(Message.CreateConfigurationLoading(request))
            try {
                val response = employeesRepository.createEmployee(request)
                syncPublish(CreateEmployeeStore.Label.OnEmployeeCreated(response.employee))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e, request))
            }
        }
    }

    private object ReducerImpl : Reducer<CreateEmployeeStore.State, Message> {
        override fun CreateEmployeeStore.State.reduce(msg: Message): CreateEmployeeStore.State {
            return when (msg) {
                is Message.CreateEmployee -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.CreateConfigurationLoading -> msg.reduce()
            }
        }

        private fun Message.CreateConfigurationLoading.reduce(): CreateEmployeeStore.State =
            CreateEmployeeStore.State.CreateEmployeeLoading(request)


        private fun Message.CreateEmployee.reduce(): CreateEmployeeStore.State =
            CreateEmployeeStore.State.CreateEmployee(request)


        private fun Message.Error.reduce(): CreateEmployeeStore.State {
            return when (exception) {
                else -> CreateEmployeeStore.State.Error.Unknown(request)
            }
        }

    }

    sealed class Message {

        data class CreateEmployee(val request: CreateEmployeeRequestData? = null) :
            Message()

        data class CreateConfigurationLoading(val request: CreateEmployeeRequest) : Message()


        data class Error(
            val exception: Exception,
            val request: CreateEmployeeRequest,
        ) : Message()

    }
}