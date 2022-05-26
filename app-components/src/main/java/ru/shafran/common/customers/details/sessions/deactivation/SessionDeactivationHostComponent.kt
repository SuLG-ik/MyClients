package ru.shafran.common.customers.details.sessions.deactivation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.companies.data.Company
import ru.shafran.network.session.DeactivateSessionStore
import ru.shafran.network.session.data.DeactivateSessionRequest
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.session.data.Session
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class SessionDeactivationHostComponent(
    componentContext: ComponentContext,
    private val session: Session,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
    private val company: Company,
) : SessionDeactivationHost, Updatable, ComponentContext by componentContext {

    private val store = getStore<DeactivateSessionStore>().reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)

    private fun reduceLabel(label: DeactivateSessionStore.Label) {
        when(label) {
            is DeactivateSessionStore.Label.OnSessionDeactivated -> onBackAndUpdate()
        }
    }

    private fun reduceState(state: DeactivateSessionStore.State) {
        when (state) {
            is DeactivateSessionStore.State.DeactivateSession ->
                router.replaceAll(SessionDeactivationHost.Configuration.DeactivateSession(state.request))
            is DeactivateSessionStore.State.DeactivateSessionLoading ->
                router.replaceAll(SessionDeactivationHost.Configuration.DeactivateSessionLoading(state.request))
            is DeactivateSessionStore.State.Error.Unknown ->
                router.replaceAll(SessionDeactivationHost.Configuration.UnknownError(state.request))
        }
    }

    override val onUpdate: (() -> Unit) = { onUpdateWithData() }

    private fun onUpdateWithData(data: DeactivateSessionRequestData? = null) {
        store.accept(DeactivateSessionStore.Intent.LoadDetails(session.id, data))
    }

    private val onApply: (DeactivateSessionRequest) -> Unit = {
        store.accept(DeactivateSessionStore.Intent.DeactivateSession(it))
    }


    private fun createChild(
        configuration: SessionDeactivationHost.Configuration,
        componentContext: ComponentContext,
    ): SessionDeactivationHost.Child {
        return when (configuration) {
            is SessionDeactivationHost.Configuration.DeactivateSession ->
                SessionDeactivationHost.Child.DeactivateSession(
                    SessionDeactivatingComponent(
                        componentContext,
                        session = session,
                        data = configuration.data,
                        onApply = { onApply(DeactivateSessionRequest(session.id, it)) },
                        onBack = onBack,
                        company = company,
                    )
                )
            is SessionDeactivationHost.Configuration.DeactivateSessionLoading ->
                SessionDeactivationHost.Child.Loading(
                    LoadingComponent(
                        message = R.string.sessions_deactivationg_loading
                    )
                )
            is SessionDeactivationHost.Configuration.UnknownError ->
                SessionDeactivationHost.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = { onUpdateWithData(configuration.request.data) },
                    )
                )
        }
    }


    override val routerState: Value<RouterState<SessionDeactivationHost.Configuration, SessionDeactivationHost.Child>>
        get() = router.state

    private val router =
        router<SessionDeactivationHost.Configuration, SessionDeactivationHost.Child>(
            initialConfiguration = SessionDeactivationHost.Configuration.DeactivateSession(null),
            childFactory = this::createChild,
        )


}