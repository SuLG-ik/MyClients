package ru.shafran.common.services.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.services.details.ServicesDetailsHostComponent
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.common.utils.stores
import ru.shafran.network.services.ServicesListStore
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

internal class ServicesListHostComponent(
    componentContext: ComponentContext,
) : ServicesListHost, ComponentContext by componentContext {


    override val serviceDetails: ServicesDetailsHost =
        ServicesDetailsHostComponent(childContext("service_details"))

    private val servicesListStore: ServicesListStore by stores()

    private val router = router<ServicesListHost.Configuration, ServicesListHost.Child>(
        initialConfiguration = ServicesListHost.Configuration.Loading,
        childFactory = this::createChild,
        key = "service_list_host",
        handleBackButton = false,
    )

    override val routerState: Value<RouterState<ServicesListHost.Configuration, ServicesListHost.Child>>
        get() = router.state

    private fun createChild(
        configuration: ServicesListHost.Configuration,
        componentContext: ComponentContext,
    ): ServicesListHost.Child {
        return when (configuration) {
            is ServicesListHost.Configuration.Loading ->
                configuration.create(componentContext)
            is ServicesListHost.Configuration.ServicesList ->
                configuration.create(componentContext)
            is ServicesListHost.Configuration.UnknownError ->
                configuration.create(componentContext)

        }
    }

    private fun ServicesListHost.Configuration.ServicesList.create(
        componentContext: ComponentContext,
    ): ServicesListHost.Child {
        return ServicesListHost.Child.ServicesList(
            ServicesListComponent(
                services = services,
                onLoading = ::onLoading,
                onCreateService = ::onCreateService,
                onSelectService = ::onSelectService,
            )
        )
    }

    private fun ServicesListHost.Configuration.Loading.create(
        componentContext: ComponentContext,
    ): ServicesListHost.Child {
        return ServicesListHost.Child.Loading(
            LoadingComponent(R.string.services_list_loading_message)
        )
    }

    private fun ServicesListHost.Configuration.UnknownError.create(
        componentContext: ComponentContext,
    ): ServicesListHost.Child {
        return ServicesListHost.Child.Error(
            ErrorComponent(R.string.unknwon_error, R.drawable.error, onContinue = ::onLoading)
        )
    }


        private fun reduceState(state: ServicesListStore.State) {
            when (state) {
                is ServicesListStore.State.Error -> reduceErrorState(state)
                is ServicesListStore.State.Loading -> reduceLoadingState(state)
                is ServicesListStore.State.ServicesLoaded -> reduceSuccessLoadingState(state)
            }
        }

        private fun reduceSuccessLoadingState(state: ServicesListStore.State.ServicesLoaded) {
            router.bringToFront(ServicesListHost.Configuration.ServicesList(state.services))
        }

        private fun reduceLoadingState(state: ServicesListStore.State.Loading) {
            router.bringToFront(ServicesListHost.Configuration.Loading)
        }

        private fun reduceErrorState(state: ServicesListStore.State.Error) {
            router.bringToFront(ServicesListHost.Configuration.UnknownError)
        }

        private fun reduceLabel(label: ServicesListStore.Label) {
            TODO()
        }

        private fun onLoading() {
            servicesListStore.accept(ServicesListStore.Intent.LoadServices())
        }

        private fun onCreateService() {
            TODO()
        }

        private fun onSelectService(service: Service) {
            serviceDetails.onShowServiceDetails(service)
        }

        init {
            lifecycle.doOnCreate {
                servicesListStore.accept(ServicesListStore.Intent.LoadServices())
                servicesListStore.reduceStates(this, this::reduceState)
                servicesListStore.reduceLabels(this, this::reduceLabel)
            }
        }

    }