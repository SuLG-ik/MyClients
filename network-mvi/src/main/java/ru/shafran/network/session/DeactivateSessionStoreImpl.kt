package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.session.data.DeactivateSessionRequest
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor


class DeactivateSessionStoreImpl(
    storeFactory: StoreFactory,
    sessionsRepository: SessionsRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : DeactivateSessionStore,
    Store<DeactivateSessionStore.Intent, DeactivateSessionStore.State, DeactivateSessionStore.Label> by storeFactory.create(
        name = "DeactivateSessionStore",
        initialState = DeactivateSessionStore.State.DeactivateSession(null),
        executorFactory = {
            Executor(
                sessionsRepository = sessionsRepository,
                coroutineDispatcher = coroutineDispatcher,
            )
        },
        reducer = ReducerImpl
    ) {


    private class Executor(
        private val sessionsRepository: SessionsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<DeactivateSessionStore.Intent, Nothing, DeactivateSessionStore.State, Message, DeactivateSessionStore.Label>(
            coroutineDispatcher) {

        override suspend fun execute(
            intent: DeactivateSessionStore.Intent,
            getState: () -> DeactivateSessionStore.State,
        ) {
            when (intent) {
                is DeactivateSessionStore.Intent.DeactivateSession ->
                    intent.execute()
                is DeactivateSessionStore.Intent.LoadDetails ->
                    syncDispatch(Message.DeactivateSession(intent.request))
            }
        }

        private suspend fun DeactivateSessionStore.Intent.DeactivateSession.execute() {
            syncDispatch(Message.DeactivateSessionLoading(request))
            try {
                val response =
                    sessionsRepository.deactivateSession(request)
                syncPublish(DeactivateSessionStore.Label.OnSessionDeactivated(response.session))
            } catch (e: Exception) {
                syncDispatch(Message.Error(e, request))
            }
        }
    }

    private object ReducerImpl : Reducer<DeactivateSessionStore.State, Message> {
        override fun DeactivateSessionStore.State.reduce(msg: Message): DeactivateSessionStore.State {
            return when (msg) {
                is Message.DeactivateSession -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.DeactivateSessionLoading -> msg.reduce()
            }
        }

        private fun Message.DeactivateSessionLoading.reduce(): DeactivateSessionStore.State =
            DeactivateSessionStore.State.DeactivateSessionLoading(request)


        private fun Message.DeactivateSession.reduce(): DeactivateSessionStore.State =
            DeactivateSessionStore.State.DeactivateSession(request)


        private fun Message.Error.reduce(): DeactivateSessionStore.State {
            return when (exception) {
                else -> DeactivateSessionStore.State.Error.Unknown(request)
            }
        }

    }

    sealed class Message {

        data class DeactivateSession(
            val request: DeactivateSessionRequestData?,
        ) : Message()

        data class DeactivateSessionLoading(val request: DeactivateSessionRequest) : Message()

        data class Error(
            val exception: Exception,
            val request: DeactivateSessionRequest,
        ) : Message()

    }
}
