package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.services.data.CreateServiceRequest
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

class CreateServiceStoreImpl(
    storeFactory: StoreFactory,
    servicesRepository: ServicesRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CreateServiceStore,
    Store<CreateServiceStore.Intent, CreateServiceStore.State, CreateServiceStore.Label> by storeFactory.create(
        name = "CreateServiceStore",
        initialState = CreateServiceStore.State.CreateService(),
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
        CancelableSyncCoroutineExecutor<CreateServiceStore.Intent, Nothing, CreateServiceStore.State, Message, CreateServiceStore.Label>(
            coroutineDispatcher) {

        override suspend fun execute(
            intent: CreateServiceStore.Intent,
            getState: () -> CreateServiceStore.State,
        ) {
            when (intent) {
                is CreateServiceStore.Intent.CreateService ->
                    intent.execute()
                is CreateServiceStore.Intent.LoadDetails ->
                    syncDispatch(Message.CreateService(intent.request))
            }
        }

        private suspend fun CreateServiceStore.Intent.CreateService.execute() {
            syncDispatch(Message.CreateServiceLoading(request))
            try {
                val response = servicesRepository.createService(request)
                syncPublish(CreateServiceStore.Label.OnServiceCreated(response.service))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e, request))
            }
        }
    }

    private object ReducerImpl : Reducer<CreateServiceStore.State, Message> {
        override fun CreateServiceStore.State.reduce(msg: Message): CreateServiceStore.State {
            return when (msg) {
                is Message.CreateService -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.CreateServiceLoading -> msg.reduce()
            }
        }

        private fun Message.CreateServiceLoading.reduce(): CreateServiceStore.State =
            CreateServiceStore.State.CreateServiceLoading(request)


        private fun Message.CreateService.reduce(): CreateServiceStore.State =
            CreateServiceStore.State.CreateService(request)


        private fun Message.Error.reduce(): CreateServiceStore.State {
            return when (exception) {
                else -> CreateServiceStore.State.Error.Unknown(request)
            }
        }

    }

    sealed class Message {

        data class CreateService(val request: CreateServiceRequest? = null) : Message()
        data class CreateServiceLoading(val request: CreateServiceRequest) : Message()


        data class Error(
            val exception: Exception,
            val request: CreateServiceRequest,
        ) : Message()

    }
}