package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shafran.network.services.data.GetAllServicesRequest
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.SyncCoroutineExecutor

internal class ServicesListStoreFactory(
    private val storeFactory: StoreFactory,
    private val servicesRepository: ServicesRepository,
) {

    fun build(): ServicesListStore = object : ServicesListStore,
        Store<ServicesListStore.Intent, ServicesListStore.State, ServicesListStore.Label> by storeFactory.create(
            name = "ServicesListStore",
            initialState = ServicesListStore.State.Loading(emptyList()),
            reducer = ReducerImpl,
            executorFactory = { Executor(servicesRepository = servicesRepository) },
        ) {}

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
            TODO()
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
    ) :
        SyncCoroutineExecutor<ServicesListStore.Intent, Nothing, ServicesListStore.State, Message, ServicesListStore.Label>() {


        override fun executeIntent(
            intent: ServicesListStore.Intent,
            getState: () -> ServicesListStore.State,
        ) {
            scope.launch(Dispatchers.IO) {
                when (intent) {
                    is ServicesListStore.Intent.LoadServices ->
                        intent.execute()
                }
            }
        }

        private suspend fun ServicesListStore.Intent.LoadServices.execute() {
            syncDispatch(Message.Loading())
            val services = servicesRepository.getAllServices(GetAllServicesRequest())
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

