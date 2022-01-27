package ru.shafran.common.details.host

import com.arkivanov.decompose.ComponentContext
import ru.shafran.common.details.edit.CustomerEditingHostComponent
import ru.shafran.common.details.info.CustomerInfoHostComponent
import ru.shafran.common.details.sessions.SessionActivationHostComponent
import ru.shafran.network.customers.data.Customer

internal class CustomerDetailsChildFactory(
    private val onBack: () -> Unit,
    private val onEdit: (Customer) -> Unit,
    private val onActivateSession: (Customer) -> Unit,
) : (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child {

    override fun invoke(
        configuration: CustomerDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): CustomerDetailsHost.Child = when (configuration) {
        is CustomerDetailsHost.Configuration.CustomerInfo ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.EditCustomer ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.Hidden ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionActivation ->
            configuration.create(componentContext)
    }

    private fun CustomerDetailsHost.Configuration.Hidden.create(componentContext: ComponentContext): CustomerDetailsHost.Child {
        return CustomerDetailsHost.Child.Hidden
    }

    private fun CustomerDetailsHost.Configuration.SessionActivation.create(componentContext: ComponentContext): CustomerDetailsHost.Child {
        return CustomerDetailsHost.Child.SessionActivation(
            SessionActivationHostComponent(
                componentContext = componentContext,
                customerId = customerId,
                onBack = onBack
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.EditCustomer.create(componentContext: ComponentContext): CustomerDetailsHost.Child {
        return CustomerDetailsHost.Child.EditCustomer(
            CustomerEditingHostComponent(
                componentContext = componentContext,
                customerId = customerId,
                onBack = onBack,
            )
        )
    }


    private fun CustomerDetailsHost.Configuration.CustomerInfo.create(componentContext: ComponentContext): CustomerDetailsHost.Child {
        return CustomerDetailsHost.Child.CustomerInfo(
            CustomerInfoHostComponent(
                componentContext = componentContext,
                customerToken = customerToken,
                onBack = onBack,
                onEdit = { onEdit(it) },
                onActivateSession = { onActivateSession(it) }
            )
        )
    }


}

