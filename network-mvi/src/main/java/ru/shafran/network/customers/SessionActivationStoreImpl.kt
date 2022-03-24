package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.EmployeesRepository
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetAllEmployeesRequest
import ru.shafran.network.services.ServicesRepository
import ru.shafran.network.services.data.GetAllServicesRequest
import ru.shafran.network.services.data.Service
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

internal class SessionActivationStoreImpl(
    private val factory: StoreFactory,
    private val sessionsRepository: SessionsRepository,
    private val servicesRepository: ServicesRepository,
    private val employeesRepository: EmployeesRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : SessionActivationStore,
            Store<SessionActivationStore.Intent, SessionActivationStore.State, SessionActivationStore.Label> by factory.create(
                name = "CustomerActivationStore",
                initialState = SessionActivationStore.State.Empty,
                executorFactory = {
                    Executor(
                        sessionsRepository = sessionsRepository,
                        servicesRepository = servicesRepository,
                        employeesRepository = employeesRepository,
                        coroutineDispatcher = coroutineDispatcher,
                    )
                },
                reducer = ReducerImpl
            ) {

    private object ReducerImpl : Reducer<SessionActivationStore.State, Message> {
        override fun SessionActivationStore.State.reduce(msg: Message): SessionActivationStore.State {
            return when (msg) {
                is Message.DetailsLoaded -> SessionActivationStore.State.DetailsLoaded(
                    customer = msg.customer,
                    services = msg.services,
                    employees = msg.employees,
                )
                is Message.Empty -> SessionActivationStore.State.Empty
                is Message.DetailsLoading -> SessionActivationStore.State.DetailsLoading()
                is Message.Error -> TODO()
            }
        }
    }

    private class Executor(
        private val sessionsRepository: SessionsRepository,
        private val servicesRepository: ServicesRepository,
        private val employeesRepository: EmployeesRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<SessionActivationStore.Intent, Nothing, SessionActivationStore.State, Message, SessionActivationStore.Label>(coroutineDispatcher) {
        override suspend fun execute(
            intent: SessionActivationStore.Intent,
            getState: () -> SessionActivationStore.State,
        ) {
            when (intent) {
                is SessionActivationStore.Intent.Activate ->
                    intent.execute()
                is SessionActivationStore.Intent.LoadDetailsWithCustomer ->
                    intent.execute()
            }
        }

        private suspend fun SessionActivationStore.Intent.Activate.execute() {
            sessionsRepository.createSessionsForCustomer(request)
            syncPublish(SessionActivationStore.Label.ActivateCompleted)
            syncDispatch(Message.Empty)
        }

        private suspend fun SessionActivationStore.Intent.LoadDetailsWithCustomer.execute() {
            coroutineScope {
                val services =
                    async { servicesRepository.getAllServices(GetAllServicesRequest()).services }
                val employees =
                    async { employeesRepository.getAllEmployees(GetAllEmployeesRequest()).employees }
                syncDispatch(Message.DetailsLoaded(
                    customer = customer,
                    services = services.await(),
                    employees = employees.await(),
                ))
            }
        }


    }

    private sealed class Message {

        object Empty : Message()

        data class DetailsLoaded(
            val customer: Customer.ActivatedCustomer,
            val services: List<Service>,
            val employees: List<Employee>,
        ) : Message()

        class DetailsLoading() : Message()

        class Error(val exception: Exception) : Message()

    }

}