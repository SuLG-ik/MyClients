package ru.shafran.common.customers.details.generator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Share
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.customers.GenerateCustomerStore
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditableCustomerData
import ru.shafran.network.utils.reduceStates

class CardGeneratorHostComponent(
    componentContext: ComponentContext,
    private val onProfile: (Customer.ActivatedCustomer) -> Unit,
    private val share: Share,
) : CardGeneratorHost,
    ComponentContext by componentContext {

    private val store = getStore<GenerateCustomerStore>().reduceStates(this, this::reduceState)

    private fun onUpdateWithData(data: EditableCustomerData? = null) {
        store.accept(GenerateCustomerStore.Intent.LoadDetails(data))
    }

    private fun createChild(
        configuration: CardGeneratorHost.Configuration,
        componentContext: ComponentContext,
    ): CardGeneratorHost.Child {
        return when (configuration) {
            is CardGeneratorHost.Configuration.CardGenerator ->
                CardGeneratorHost.Child.CardGenerator(
                    CardGeneratorComponent(data = configuration.data, onGenerate = this::onGenerate)
                )
            is CardGeneratorHost.Configuration.CardSender ->
                CardGeneratorHost.Child.CardSender(CardSenderComponent(componentContext,
                    configuration.token,
                    configuration.customer,
                    this::onProfile,
                    share = share))
            is CardGeneratorHost.Configuration.Loading ->
                CardGeneratorHost.Child.Loading(
                    LoadingComponent(
                        R.string.customers_card_generate_loading
                    )
                )
            is CardGeneratorHost.Configuration.UnknownError ->
                CardGeneratorHost.Child.Error(
                    ErrorComponent(
                        R.string.unknwon_error,
                        R.drawable.error,
                        onContinue = { onUpdateWithData(configuration.data.data) }
                    )
                )
        }
    }

    private fun onProfile(customer: Customer.ActivatedCustomer) {
        onProfile.invoke(customer)
    }

    private fun onGenerate(request: CreateCustomerRequest) {
        store.accept(GenerateCustomerStore.Intent.GenerateCustomer(request))
    }

    override val routerState: Value<RouterState<CardGeneratorHost.Configuration, CardGeneratorHost.Child>>
        get() = router.state

    private fun reduceState(state: GenerateCustomerStore.State) {
        return when (state) {
            is GenerateCustomerStore.State.CustomerGenerated ->
                router.replaceAll(CardGeneratorHost.Configuration.CardSender(state.token,
                    state.customer))
            is GenerateCustomerStore.State.Error.Unknown ->
                router.replaceAll(CardGeneratorHost.Configuration.UnknownError(state.request))
            is GenerateCustomerStore.State.Loading ->
                router.replaceAll(CardGeneratorHost.Configuration.Loading)
            is GenerateCustomerStore.State.Request ->
                router.replaceAll(CardGeneratorHost.Configuration.CardGenerator(state.data))
        }
    }

    private val router = router<CardGeneratorHost.Configuration, CardGeneratorHost.Child>(
        initialConfiguration = CardGeneratorHost.Configuration.CardGenerator(null),
        childFactory = this::createChild
    )
}