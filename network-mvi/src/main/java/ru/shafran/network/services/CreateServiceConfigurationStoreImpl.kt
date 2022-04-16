package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.services.data.CreateConfigurationRequest
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

class CreateServiceConfigurationStoreImpl(
    storeFactory: StoreFactory,
    servicesRepository: ServicesRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CreateServiceConfigurationStore,
    Store<CreateServiceConfigurationStore.Intent, CreateServiceConfigurationStore.State, CreateServiceConfigurationStore.Label> by storeFactory.create(
        name = "CreateServiceConfigurationStore",
        initialState = CreateServiceConfigurationStore.State.CreateConfiguration(),
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
        CancelableSyncCoroutineExecutor<CreateServiceConfigurationStore.Intent, Nothing, CreateServiceConfigurationStore.State, Message, CreateServiceConfigurationStore.Label>(
            coroutineDispatcher) {

        override suspend fun execute(
            intent: CreateServiceConfigurationStore.Intent,
            getState: () -> CreateServiceConfigurationStore.State,
        ) {
            when (intent) {
                is CreateServiceConfigurationStore.Intent.CreateConfiguration ->
                    intent.execute()
                is CreateServiceConfigurationStore.Intent.LoadDetails ->
                    syncDispatch(Message.CreateConfiguration(intent.request))
            }
        }

        private suspend fun CreateServiceConfigurationStore.Intent.CreateConfiguration.execute() {
            syncDispatch(Message.CreateConfigurationLoading(request))
            try {
                val response = servicesRepository.createConfiguration(request)
                syncPublish(CreateServiceConfigurationStore.Label.OnConfigurationCreated(response.service))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e, request))
            }
        }
    }

    private object ReducerImpl : Reducer<CreateServiceConfigurationStore.State, Message> {
        override fun CreateServiceConfigurationStore.State.reduce(msg: Message): CreateServiceConfigurationStore.State {
            return when (msg) {
                is Message.CreateConfiguration -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.CreateConfigurationLoading -> msg.reduce()
            }
        }

        private fun Message.CreateConfigurationLoading.reduce(): CreateServiceConfigurationStore.State =
            CreateServiceConfigurationStore.State.CreateConfigurationLoading(request)


        private fun Message.CreateConfiguration.reduce(): CreateServiceConfigurationStore.State =
            CreateServiceConfigurationStore.State.CreateConfiguration(request)


        private fun Message.Error.reduce(): CreateServiceConfigurationStore.State {
            return when (exception) {
                else -> CreateServiceConfigurationStore.State.Error.Unknown(request)
            }
        }

    }

    sealed class Message {

        data class CreateConfiguration(val request: CreateConfigurationRequestData? = null) :
            Message()

        data class CreateConfigurationLoading(val request: CreateConfigurationRequest) : Message()


        data class Error(
            val exception: Exception,
            val request: CreateConfigurationRequest,
        ) : Message()

    }
}