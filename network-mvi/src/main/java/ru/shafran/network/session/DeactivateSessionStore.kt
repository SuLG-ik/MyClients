package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.session.data.DeactivateSessionRequest
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.session.data.Session

interface DeactivateSessionStore :
    Store<DeactivateSessionStore.Intent, DeactivateSessionStore.State, DeactivateSessionStore.Label> {

    sealed class Intent {

        data class LoadDetails(
            val sessionId: String,
            val request: DeactivateSessionRequestData? = null,
        ) : Intent()

        data class DeactivateSession(
            val request: DeactivateSessionRequest,
        ) : Intent()

    }

    sealed class State {

        data class DeactivateSession(
            val request: DeactivateSessionRequestData?,
        ) : State()

        data class DeactivateSessionLoading(val request: DeactivateSessionRequest) : State()

        sealed class Error : State() {
            abstract val request: DeactivateSessionRequest

            data class Unknown(override val request: DeactivateSessionRequest) : Error()
        }

    }

    sealed class Label {

        data class OnSessionDeactivated(val sessions: Session) : Label()

    }


}