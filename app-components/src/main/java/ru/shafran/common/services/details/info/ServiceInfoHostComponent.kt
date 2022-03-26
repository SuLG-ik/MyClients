package ru.shafran.common.services.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.getStore
import ru.shafran.network.services.ServiceInfoStore
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceStates

class ServiceInfoHostComponent(
    componentContext: ComponentContext,
    private val serviceId: String? = null,
    private val service: Service? = null,
) :
    ComponentContext by componentContext, ServiceInfoHost, Updatable {


    private val store = getStore<ServiceInfoStore>()

    private val router = router<ServiceInfoHost.Configuration, ServiceInfoHost.Child>(
        initialConfiguration = ServiceInfoHost.Configuration.Empty,
        childFactory = this::createChild
    )

    private fun createChild(
        configuration: ServiceInfoHost.Configuration,
        componentContext: ComponentContext,
    ): ServiceInfoHost.Child {
        return when (configuration) {
            is ServiceInfoHost.Configuration.Empty -> ServiceInfoHost.Child.Loading(
                LoadingComponent(
                    R.string.services_details_loading
                )
            )
            is ServiceInfoHost.Configuration.Loading -> ServiceInfoHost.Child.Loading(
                LoadingComponent(
                    R.string.services_details_loading
                )
            )
            is ServiceInfoHost.Configuration.ServiceLoaded ->
                ServiceInfoHost.Child.Loaded(LoadedServiceInfoComponent(configuration.service))
        }
    }

    override val routerState: Value<RouterState<ServiceInfoHost.Configuration, ServiceInfoHost.Child>>
        get() = router.state

    private fun reduceStates(state: ServiceInfoStore.State) {
        when (state) {
            is ServiceInfoStore.State.Empty ->
                state.reduce()
            ServiceInfoStore.State.Error.Unknown -> TODO()
            is ServiceInfoStore.State.Loading ->
                state.reduce()
            is ServiceInfoStore.State.ServiceLoaded ->
                state.reduce()
        }
    }

    private fun ServiceInfoStore.State.Loading.reduce() {
        router.replaceCurrent(ServiceInfoHost.Configuration.Loading)
    }

    private fun ServiceInfoStore.State.Empty.reduce() {
        onUpdate()
    }


    private fun ServiceInfoStore.State.ServiceLoaded.reduce() {
        router.replaceCurrent(ServiceInfoHost.Configuration.ServiceLoaded(service = service))
    }


    override fun onUpdate() {
        if (service != null)
            store.accept(ServiceInfoStore.Intent.LoadServiceWithData(service))
        else if (serviceId != null)
            store.accept(ServiceInfoStore.Intent.LoadServiceWithId(serviceId))
    }

    init {
        if (serviceId == null && service == null) throw IllegalStateException("serviceId and service can not be null together")
        store.reduceStates(this, this::reduceStates)
    }

}