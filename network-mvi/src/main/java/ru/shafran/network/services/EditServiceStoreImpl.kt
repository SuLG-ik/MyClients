package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.services.data.EditServiceRequest
import ru.shafran.network.services.data.EditableServiceData
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

class EditServiceStoreImpl(
    storeFactory: StoreFactory,
    servicesRepository: ServicesRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : EditServiceStore,
    Store<EditServiceStore.Intent, EditServiceStore.State, EditServiceStore.Label> by storeFactory.create(
        name = "EditServiceStore",
        initialState = EditServiceStore.State.Empty,
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
        CancelableSyncCoroutineExecutor<EditServiceStore.Intent, Nothing, EditServiceStore.State, Message, EditServiceStore.Label>(
            coroutineDispatcher) {

        override suspend fun execute(
            intent: EditServiceStore.Intent,
            getState: () -> EditServiceStore.State,
        ) {
            when (intent) {
                is EditServiceStore.Intent.EditService ->
                    intent.execute()
                is EditServiceStore.Intent.LoadDetails ->
                    syncDispatch(Message.EditService(intent.request))
            }
        }

        private suspend fun EditServiceStore.Intent.EditService.execute() {
            syncDispatch(Message.EditServiceLoading(request))
            try {
                val response = servicesRepository.editService(request)
                syncPublish(EditServiceStore.Label.OnServiceEdited(response.service))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e, request))
            }
        }
    }

    private object ReducerImpl : Reducer<EditServiceStore.State, Message> {
        override fun EditServiceStore.State.reduce(msg: Message): EditServiceStore.State {
            return when (msg) {
                is Message.EditService -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.EditServiceLoading -> msg.reduce()
            }
        }

        private fun Message.EditServiceLoading.reduce(): EditServiceStore.State =
            EditServiceStore.State.EditServiceLoading(request)


        private fun Message.EditService.reduce(): EditServiceStore.State =
            EditServiceStore.State.EditService(request)


        private fun Message.Error.reduce(): EditServiceStore.State {
            return when (exception) {
                else -> EditServiceStore.State.Error.Unknown(request)
            }
        }

    }

    sealed class Message {

        data class EditService(val request: EditableServiceData) : Message()
        data class EditServiceLoading(val request: EditServiceRequest) : Message()


        data class Error(
            val exception: Exception,
            val request: EditServiceRequest,
        ) : Message()

    }
}