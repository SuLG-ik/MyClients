package ru.shafran.common.sessions.history

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.customers.details.host.CustomerDetailsHost
import ru.shafran.common.customers.details.host.CustomerDetailsHostComponent
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Share
import ru.shafran.common.utils.getStore
import ru.shafran.network.session.SessionsUsageHistoryStore
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class SessionsHistoryListHostComponent(
    componentContext: ComponentContext,
    share: Share,
) : SessionsHistoryListHost, ComponentContext by componentContext {

    override val customerDetails: CustomerDetailsHost =
        CustomerDetailsHostComponent(componentContext, share)


    val store = getStore<SessionsUsageHistoryStore>()
        .reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)

    private fun reduceState(state: SessionsUsageHistoryStore.State) {
        when (state) {
            is SessionsUsageHistoryStore.State.Empty ->
                onUpdate()
            is SessionsUsageHistoryStore.State.Error ->
                router.bringToFront(SessionsHistoryListHost.Configuration.UnknownError)
            is SessionsUsageHistoryStore.State.HistoryLoaded ->
                router.bringToFront(SessionsHistoryListHost.Configuration.SessionsHistoryList(state.history))
            is SessionsUsageHistoryStore.State.Loading ->
                router.bringToFront(SessionsHistoryListHost.Configuration.Loading)
        }
    }

    private fun reduceLabel(label: SessionsUsageHistoryStore.Label) {

    }

    private val router =
        router<SessionsHistoryListHost.Configuration, SessionsHistoryListHost.Child>(
            initialConfiguration = SessionsHistoryListHost.Configuration.Loading,
            childFactory = this::createChild
        )

    private fun createChild(
        configuration: SessionsHistoryListHost.Configuration,
        componentContext: ComponentContext,
    ): SessionsHistoryListHost.Child {
        return when (configuration) {
            is SessionsHistoryListHost.Configuration.Loading -> SessionsHistoryListHost.Child.Loading(
                LoadingComponent(R.string.customers_session_activation_details_loading_message)
            )
            is SessionsHistoryListHost.Configuration.SessionsHistoryList -> SessionsHistoryListHost.Child.LastSessionsList(
                SessionsHistoryListComponent(
                    history = configuration.history,
                    onUpdate = onUpdate,
                    onStats = customerDetails.onShowStats,
                    onDetails = customerDetails.onShowCustomer,
                )
            )
            is SessionsHistoryListHost.Configuration.UnknownError -> SessionsHistoryListHost.Child.Error(
                ErrorComponent(
                    R.string.sessions_details_loading_error,
                    R.drawable.error,
                    onContinue = onUpdate,
                ))
        }
    }

    private val onUpdate: () -> Unit = {
        store.accept(SessionsUsageHistoryStore.Intent.LoadHistory())
    }

    override val routerState: Value<RouterState<SessionsHistoryListHost.Configuration, SessionsHistoryListHost.Child>>
        get() = router.state


}