package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class SessionActivationStoreImpl(
    private val factory: StoreFactory,
    private val sessionsRepository: SessionsRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : SessionActivationStore,
    Store<SessionActivationStore.Intent, SessionActivationStore.State, SessionActivationStore.Label> by factory.create(
        name = "CustomerActivationStore",
        initialState = SessionActivationStore.State.Empty,
        executorFactory = {
            Executor(
                sessionsRepository = sessionsRepository,
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
                )
                is Message.Empty -> SessionActivationStore.State.Empty
                is Message.DetailsLoading -> SessionActivationStore.State.DetailsLoading()
                is Message.Error -> msg.reduce()
            }
        }

        private fun Message.Error.reduce(): SessionActivationStore.State {
            return when (exception) {
                else -> {
                    Napier.e({ "Unknown exception" }, exception)
                    SessionActivationStore.State.Error.Unknown
                }
            }
        }


    }

    private class Executor(
        private val sessionsRepository: SessionsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) : SafeCancelableSyncCoroutineExecutor<SessionActivationStore.Intent, Nothing, SessionActivationStore.State, Message, SessionActivationStore.Label>(coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
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
            syncDispatch(Message.DetailsLoaded(
                customer = customer,
            ))
        }


    }

    private sealed class Message {

        object Empty : Message()

        data class DetailsLoaded(
            val customer: Customer.ActivatedCustomer,
        ) : Message()

        class DetailsLoading() : Message()

        class Error(val exception: Exception) : Message()

    }

}