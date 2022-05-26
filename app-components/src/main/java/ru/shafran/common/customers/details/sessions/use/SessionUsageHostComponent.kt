package ru.shafran.common.customers.details.sessions.use

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.companies.data.Company
import ru.shafran.network.session.SessionUseStore
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class SessionUsageHostComponent(
    componentContext: ComponentContext,
    private val session: Session,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
    private val company: Company
) : SessionUsageHost, ComponentContext by componentContext {

    private val store = getStore<SessionUseStore>()

    private val router = router<SessionUsageHost.Configuration, SessionUsageHost.Child>(
        initialConfiguration = SessionUsageHost.Configuration.Loaded(session),
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: SessionUsageHost.Configuration,
        componentContext: ComponentContext,
    ): SessionUsageHost.Child {
        return when (configuration) {
            is SessionUsageHost.Configuration.DetailsLoading ->
                configuration.create()
            is SessionUsageHost.Configuration.Empty ->
                configuration.create()
            is SessionUsageHost.Configuration.Loaded ->
                configuration.create(componentContext)
        }
    }

    private fun SessionUsageHost.Configuration.DetailsLoading.create(): SessionUsageHost.Child {
        return SessionUsageHost.Child.Loading(
            LoadingComponent(R.string.customers_loading)
        )
    }

    private fun SessionUsageHost.Configuration.Empty.create(): SessionUsageHost.Child {
        return SessionUsageHost.Child.Loading(
            LoadingComponent(R.string.customers_loading)
        )
    }

    private fun SessionUsageHost.Configuration.Loaded.create(componentContext: ComponentContext): SessionUsageHost.Child {
        return SessionUsageHost.Child.Loaded(
            SessionUsingComponent(
                componentContext = componentContext,
                session = session,
                onUse = ::onUse,
                onBack = ::onBack,
                company = company,
            )
        )
    }

    private fun onUse(request: UseSessionRequest) {
        store.accept(SessionUseStore.Intent.UseSession(request))
    }

    private fun onBack() {
        onBack.invoke()
    }


    override val routerState: Value<RouterState<SessionUsageHost.Configuration, SessionUsageHost.Child>>
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
        router.replaceAll(SessionUsageHost.Configuration.DetailsLoading)
    }


    private fun SessionUseStore.State.SessionLoaded.reduce() {
        router.replaceAll(SessionUsageHost.Configuration.Loaded(session))
    }

    private fun SessionUseStore.State.SessionUseLoading.reduce() {
        router.replaceAll(SessionUsageHost.Configuration.DetailsLoading)
    }


    private fun SessionUseStore.State.Empty.reduce() {
        router.replaceAll(SessionUsageHost.Configuration.DetailsLoading)
        store.accept(SessionUseStore.Intent.LoadSession(session = session))

    }


    init {
        store.reduceStates(this, this::reduceStates)
            .reduceLabels(this, this::reduceLabels)
    }

}