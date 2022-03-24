package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.utils.SyncCoroutineExecutor


internal class SessionUseStoreImpl(
    private val storeFactory: StoreFactory,
    private val sessionsRepository: SessionsRepository,
    private val dispatcher: CoroutineDispatcher,
) : SessionUseStore,
    Store<SessionUseStore.Intent, SessionUseStore.State, SessionUseStore.Label> by storeFactory.create(
        name = "SessionUseStore",
        initialState = SessionUseStore.State.Empty,
        reducer = ReducerImpl,
        executorFactory = {
            Executor(
                dispatcher,
                sessionsRepository,
            )
        },
    ) {

    private object ReducerImpl :
        Reducer<SessionUseStore.State, Message> {

        override fun SessionUseStore.State.reduce(msg: Message): SessionUseStore.State {
            return when (msg) {
                is Message.Empty -> SessionUseStore.State.Empty
                is Message.SessionLoaded -> SessionUseStore.State.SessionLoaded(msg.session)
                is Message.SessionLoading -> SessionUseStore.State.SessionLoading()
                is Message.SessionUseLoading -> SessionUseStore.State.SessionUseLoading(msg.request)
                is Message.Error -> TODO()
            }
        }
    }

    private class Executor(
        private val dispatcher: CoroutineDispatcher,
        private val sessionsRepository: SessionsRepository,
    ) :
        SyncCoroutineExecutor<SessionUseStore.Intent, Nothing, SessionUseStore.State, Message, SessionUseStore.Label>() {

        private var job: Job? = null

        override fun executeIntent(
            intent: SessionUseStore.Intent,
            getState: () -> SessionUseStore.State,
        ) {
            job?.cancel()
            job = scope.launch(dispatcher) {
                when (intent) {
                    is SessionUseStore.Intent.LoadSession -> intent.execute()
                    is SessionUseStore.Intent.UseSession -> intent.execute()
                }
            }
        }

        private suspend fun SessionUseStore.Intent.LoadSession.execute() {
            syncDispatch(Message.SessionLoaded(session))
        }

        private suspend fun SessionUseStore.Intent.UseSession.execute() {
            syncDispatch(Message.SessionUseLoading(request))
            val session = sessionsRepository.useSession(request)
            syncPublish(SessionUseStore.Label.SessionUsed)
        }


    }

    private sealed class Message {

        data class SessionLoaded(
            val session: Session,
        ) : Message()

        object Empty : Message()

        class SessionLoading : Message()

        data class SessionUseLoading(val request: UseSessionRequest) : Message()

        class Error(val exception: Exception) : Message()
    }


}