package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.UseSessionRequest

interface SessionUseStore :
    Store<SessionUseStore.Intent, SessionUseStore.State, SessionUseStore.Label> {

    sealed class Intent {
        data class LoadSession(
            val session: Session,
        ) : Intent()

        data class UseSession(val request: UseSessionRequest) : Intent()
    }

    sealed class State {
        data class SessionLoaded(
            val session: Session,
        ) : State()

        object Empty : State()

        class SessionLoading : State()

        data class SessionUseLoading(val request: UseSessionRequest) : State()

        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }
    }

    sealed class Label {
        object SessionUsed : Label()
    }

}