package ru.shafran.common.details.sessions.use

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.session.SessionUseStore
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class SessionUseHostComponent(
    componentContext: ComponentContext,
    private val session: Session,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
) : SessionUseHost, ComponentContext by componentContext {

    private val store = getStore<SessionUseStore>()

    private val router = router<SessionUseHost.Configuration, SessionUseHost.Child>(
        initialConfiguration = SessionUseHost.Configuration.Loaded(session),
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: SessionUseHost.Configuration,
        componentContext: ComponentContext,
    ): SessionUseHost.Child {
        return when (configuration) {
            is SessionUseHost.Configuration.DetailsLoading ->
                configuration.create()
            is SessionUseHost.Configuration.Empty ->
                configuration.create()
            is SessionUseHost.Configuration.Loaded ->
                configuration.create(componentContext)
        }
    }

    private fun SessionUseHost.Configuration.DetailsLoading.create(): SessionUseHost.Child {
        return SessionUseHost.Child.Loading(
            LoadingComponent(R.string.customers_loading)
        )
    }

    private fun SessionUseHost.Configuration.Empty.create(): SessionUseHost.Child {
        return SessionUseHost.Child.Loading(
            LoadingComponent(R.string.customers_loading)
        )
    }

    private fun SessionUseHost.Configuration.Loaded.create(componentContext: ComponentContext): SessionUseHost.Child {
        return SessionUseHost.Child.Loaded(
            SessionUseComponent(
                componentContext = componentContext,
                session = session,
                onUse = ::onUse,
                onBack = ::onBack,
            )
        )
    }

    private fun onUse(request: UseSessionRequest) {
        store.accept(SessionUseStore.Intent.UseSession(request))
    }

    private fun onBack() {
        onBack.invoke()
    }


    override val routerState: Value<RouterState<SessionUseHost.Configuration, SessionUseHost.Child>>
        get() = router.state

    private fun reduceStates(state: SessionUseStore.State) {
        when (state) {
            is SessionUseStore.State.SessionLoading ->
                state.reduce()
            is SessionUseStore.State.SessionLoaded ->
                state.reduce()
            is SessionUseStore.State.SessionUseLoading ->
                state.reduce()
            is SessionUseStore.State.Empty ->
                state.reduce()
            is SessionUseStore.State.Error.ConnectionLost -> TODO()
            is SessionUseStore.State.Error.Internal -> TODO()
            is SessionUseStore.State.Error.Unknown -> TODO()
        }
    }

    private fun reduceLabels(label: SessionUseStore.Label) {
        when (label) {
            is SessionUseStore.Label.SessionUsed -> onBackAndUpdate()
        }
    }

    private fun SessionUseStore.State.SessionLoading.reduce() {
        router.replaceAll(SessionUseHost.Configuration.DetailsLoading)
    }


    private fun SessionUseStore.State.SessionLoaded.reduce() {
        router.replaceAll(SessionUseHost.Configuration.Loaded(session))
    }

    private fun SessionUseStore.State.SessionUseLoading.reduce() {
        router.replaceAll(SessionUseHost.Configuration.DetailsLoading)
    }


    private fun SessionUseStore.State.Empty.reduce() {
        router.replaceAll(SessionUseHost.Configuration.DetailsLoading)
        store.accept(SessionUseStore.Intent.LoadSession(session = session))

    }


    init {
        store.reduceStates(this, this::reduceStates)
            .reduceLabels(this, this::reduceLabels)
    }

}