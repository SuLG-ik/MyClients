package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.services.data.GetServiceByIdRequest
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class ServiceInfoStoreImpl(
    private val factory: StoreFactory,
    private val servicesRepository: ServicesRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ServiceInfoStore,
    Store<ServiceInfoStore.Intent, ServiceInfoStore.State, ServiceInfoStore.Label> by factory.create(
        name = "ServiceInfoStore",
        initialState = ServiceInfoStore.State.Empty,
        executorFactory = {
            Executor(
                servicesRepository = servicesRepository,
                coroutineDispatcher = coroutineDispatcher,
            )
        },
        reducer = ReducerImpl
    ) {


    private class Executor(
        private val servicesRepository: ServicesRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<ServiceInfoStore.Intent, Nothing, ServiceInfoStore.State, Message, ServiceInfoStore.Label>(
            coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: ServiceInfoStore.Intent,
            getState: () -> ServiceInfoStore.State,
        ) {
            when (intent) {
                is ServiceInfoStore.Intent.LoadServiceWithId ->
                    intent.execute()
                is ServiceInfoStore.Intent.LoadServiceWithData ->
                    syncDispatch(Message.ServiceLoaded(service = intent.service))
            }
        }

        private suspend fun ServiceInfoStore.Intent.LoadServiceWithId.execute() {
            syncDispatch(Message.Loading())
            val response = servicesRepository.getServiceById(GetServiceByIdRequest(serviceId))
            syncDispatch(Message.ServiceLoaded(response.service!!))
        }
    }

    private object ReducerImpl : Reducer<ServiceInfoStore.State, Message> {
        override fun ServiceInfoStore.State.reduce(msg: Message): ServiceInfoStore.State {
            return when (msg) {
                is Message.ServiceLoaded -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.Loading -> msg.reduce()
            }
        }


        private fun Message.ServiceLoaded.reduce(): ServiceInfoStore.State =
            ServiceInfoStore.State.ServiceLoaded(service)


        private fun Message.Loading.reduce(): ServiceInfoStore.State =
            ServiceInfoStore.State.Loading()


        private fun Message.Error.reduce(): ServiceInfoStore.State {
            return when (exception) {
                else -> ServiceInfoStore.State.Error.Unknown
            }
        }

    }

    sealed class Message {

        class Loading : Message()

        data class ServiceLoaded(
            val service: Service,
        ) : Message()

        class Error(val exception: Exception) : Message()

    }
}
