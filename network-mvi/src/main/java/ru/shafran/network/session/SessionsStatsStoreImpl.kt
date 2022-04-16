package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

class SessionsStatsStoreImpl(
    storeFactory: StoreFactory,
    sessionsRepository: SessionsRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : SessionsStatsStore,
    Store<SessionsStatsStore.Intent, SessionsStatsStore.State, SessionsStatsStore.Label> by storeFactory.create(
        name = "SessionsStatsStore",
        initialState = SessionsStatsStore.State.Empty,
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
        SafeCancelableSyncCoroutineExecutor<SessionsStatsStore.Intent, Nothing, SessionsStatsStore.State, Message, SessionsStatsStore.Label>(
            coroutineDispatcher) {

        override suspend fun safeExecute(
            intent: SessionsStatsStore.Intent,
            getState: () -> SessionsStatsStore.State,
        ) {
            when (intent) {
                is SessionsStatsStore.Intent.LoadStats ->
                    intent.execute()
            }
        }

        private suspend fun SessionsStatsStore.Intent.LoadStats.execute() {
            syncDispatch(Message.Loading())
            val response =
                sessionsRepository.getSessionsStats(request = request)
            syncDispatch(Message.StatsLoaded(response.period, response.stats))
        }

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }
    }

    private object ReducerImpl : Reducer<SessionsStatsStore.State, Message> {
        override fun SessionsStatsStore.State.reduce(msg: Message): SessionsStatsStore.State {
            return when (msg) {
                is Message.Loading -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.StatsLoaded -> msg.reduce()
            }
        }

        private fun Message.Loading.reduce(): SessionsStatsStore.State =
            SessionsStatsStore.State.Loading()


        private fun Message.StatsLoaded.reduce(): SessionsStatsStore.State =
            SessionsStatsStore.State.StatsLoaded(period = period, stats = stats)


        private fun Message.Error.reduce(): SessionsStatsStore.State {
            return when (exception) {
                else -> SessionsStatsStore.State.Error.Unknown
            }
        }

    }

    sealed class Message {

        data class StatsLoaded(val period: DatePeriod, val stats: SessionStats) : Message()

        class Loading : Message()

        data class Error(
            val exception: Exception,
        ) : Message()

    }
}