package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.services.data.GetAllServicesRequest
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class ServicesListStoreImpl(
    private val storeFactory: StoreFactory,
    private val servicesRepository: ServicesRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ServicesListStore,
    Store<ServicesListStore.Intent, ServicesListStore.State, ServicesListStore.Label> by storeFactory.create(
        name = "ServicesListStore",
        initialState = ServicesListStore.State.Empty,
        reducer = ReducerImpl,
        executorFactory = {
            Executor(
                servicesRepository = servicesRepository,
                coroutineDispatcher = coroutineDispatcher
            )
        },
    ) {

    private object ReducerImpl :
        Reducer<ServicesListStore.State, Message> {

        override fun ServicesListStore.State.reduce(msg: Message): ServicesListStore.State {
            return when (msg) {
                is Message.Error -> msg.reduce()
                is Message.Loading -> msg.reduce()
                is Message.ServicesLoaded -> msg.reduce()
            }
        }

        private fun Message.Error.reduce(): ServicesListStore.State {
            return ServicesListStore.State.Error.Unknown
        }


        private fun Message.Loading.reduce(): ServicesListStore.State {
            return ServicesListStore.State.Loading()
        }


        private fun Message.ServicesLoaded.reduce(): ServicesListStore.State {
            return ServicesListStore.State.ServicesLoaded(services = services)
        }

    }

    private class Executor(
        private val servicesRepository: ServicesRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) : SafeCancelableSyncCoroutineExecutor<ServicesListStore.Intent, Nothing, ServicesListStore.State, Message, ServicesListStore.Label>(
        coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: ServicesListStore.Intent,
            getState: () -> ServicesListStore.State,
        ) {
            when (intent) {
                is ServicesListStore.Intent.LoadServices ->
                    intent.execute()
            }
        }

        private suspend fun ServicesListStore.Intent.LoadServices.execute() {
            syncDispatch(Message.Loading())
            val services =
                servicesRepository.getAllServices(GetAllServicesRequest(companyId = companyId))
            syncDispatch(
                Message.ServicesLoaded(
                    services = services.services,
                )
            )
        }


    }

    private sealed class Message {
        data class ServicesLoaded(
            val services: List<Service>,
        ) : Message()

        data class Loading(
            val loadedServices: List<Service>? = null,
        ) : Message()

        data class Error(val exception: Exception) : Message()
    }

}
