package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.session.data.GetSessionUsagesHistoryRequest
import ru.shafran.network.session.data.SessionUsageHistoryItem
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class SessionsUsageHistoryStoreImpl(
    private val storeFactory: StoreFactory,
    sessionsRepository: SessionsRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : SessionsUsageHistoryStore,
    Store<SessionsUsageHistoryStore.Intent, SessionsUsageHistoryStore.State, SessionsUsageHistoryStore.Label> by storeFactory.create(
        name = "SessionUsageHistoryStore",
        initialState = SessionsUsageHistoryStore.State.Empty,
        reducer = ReducerImpl,
        executorFactory = { Executor(sessionsRepository, coroutineDispatcher) },
    ) {

    private object ReducerImpl :
        Reducer<SessionsUsageHistoryStore.State, Message> {

        override fun SessionsUsageHistoryStore.State.reduce(msg: Message): SessionsUsageHistoryStore.State {
            return when (msg) {
                is Message.Error -> SessionsUsageHistoryStore.State.Error.Unknown
                is Message.HistoryLoaded -> SessionsUsageHistoryStore.State.HistoryLoaded(
                    msg.offset, msg.page, msg.history
                )
                is Message.Loading -> SessionsUsageHistoryStore.State.Loading
            }
        }
    }

    private class Executor(
        private val sessionsRepository: SessionsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<SessionsUsageHistoryStore.Intent, Nothing, SessionsUsageHistoryStore.State, Message, SessionsUsageHistoryStore.Label>(
            coroutineDispatcher) {
        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: SessionsUsageHistoryStore.Intent,
            getState: () -> SessionsUsageHistoryStore.State,
        ) {
            when (intent) {
                is SessionsUsageHistoryStore.Intent.LoadHistory -> intent.execute()
            }
        }

        private suspend fun SessionsUsageHistoryStore.Intent.LoadHistory.execute() {
            syncDispatch(Message.Loading)
            val response =
                sessionsRepository.getSessionUsagesHistory(GetSessionUsagesHistoryRequest(
                    offset = offset,
                    page = page
                ))
            syncDispatch(Message.HistoryLoaded(
                offset = response.offset,
                page = response.page,
                history = response.usages
            ))
        }

    }

    private sealed class Message {
        data class HistoryLoaded(
            val offset: Int,
            val page: Int,
            val history: List<SessionUsageHistoryItem>,
        ) : Message()

        object Loading : Message()

        class Error(val exception: Exception) : Message()

    }


}