package ru.shafran.common.customers.details.sessions.activation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.stores
import ru.shafran.network.customers.SessionActivationStore
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class SessionActivationHostComponent(
    componentContext: ComponentContext,
    private val customer: Customer.ActivatedCustomer,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
) : SessionActivationHost, ComponentContext by componentContext {

    private val store by stores<SessionActivationStore>()

    private val router = router<SessionActivationHost.Configuration, SessionActivationHost.Child>(
        initialConfiguration = SessionActivationHost.Configuration.Empty,
        childFactory = this::createChild,
    )

    init {
        store.reduceStates(this, this::reduceStates)
        store.reduceLabels(this, this::reduceLabels)
    }

    private fun onActivate(request: CreateSessionForCustomerRequest) {
        store.accept(SessionActivationStore.Intent.Activate(request))
    }

    private fun reduceLabels(label: SessionActivationStore.Label) {
        when (label) {
            is SessionActivationStore.Label.ActivateCompleted -> label.reduce()
        }
    }

    private fun SessionActivationStore.Label.ActivateCompleted.reduce() {
        onBackAndUpdate.invoke()
    }

    private fun reduceStates(state: SessionActivationStore.State) {
        when (state) {
            is SessionActivationStore.State.ActivationLoading -> state.reduce()
            is SessionActivationStore.State.DetailsLoaded -> state.reduce()
            is SessionActivationStore.State.DetailsLoading -> state.reduce()
            is SessionActivationStore.State.Empty -> state.reduce()
        }
    }

    private fun SessionActivationStore.State.ActivationLoading.reduce() {
        router.bringToFront(SessionActivationHost.Configuration.ActivationLoading)
    }

    private fun SessionActivationStore.State.DetailsLoading.reduce() {
        router.bringToFront(SessionActivationHost.Configuration.DetailsLoading)
    }

    private fun SessionActivationStore.State.Empty.reduce() {
        store.accept(SessionActivationStore.Intent.LoadDetailsWithCustomer(customer = customer))
        router.bringToFront(SessionActivationHost.Configuration.DetailsLoading)
    }

    private fun SessionActivationStore.State.DetailsLoaded.reduce() {
        router.bringToFront(
            SessionActivationHost.Configuration.Loaded(
                customer = customer,
                employees = employees,
                services = services,
            )
        )
    }


    private fun createChild(
        configuration: SessionActivationHost.Configuration,
        componentContext: ComponentContext,
    ): SessionActivationHost.Child {
        return when (configuration) {
            is SessionActivationHost.Configuration.Loaded -> SessionActivationHost.Child.Loaded(
                SessionActivationComponent(
                    componentContext = componentContext,
                    customer = configuration.customer,
                    onBack = onBack,
                    onActivate = this::onActivate,
                )
            )
            is SessionActivationHost.Configuration.DetailsLoading -> SessionActivationHost.Child.Loading(
                LoadingComponent(R.string.customers_session_activation_details_loading_message)
            )
            is SessionActivationHost.Configuration.ActivationLoading -> SessionActivationHost.Child.Loading(
                LoadingComponent(R.string.customers_session_activation_details_loading_message)
            )
            SessionActivationHost.Configuration.Empty -> SessionActivationHost.Child.Loading(
                LoadingComponent(R.string.customers_session_activation_details_loading_message)
            )
        }
    }


    override val routerState: Value<RouterState<SessionActivationHost.Configuration, SessionActivationHost.Child>>
        get() = router.state
}