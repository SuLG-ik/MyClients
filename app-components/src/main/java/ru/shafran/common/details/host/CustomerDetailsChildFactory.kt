package ru.shafran.common.details.host

import com.arkivanov.decompose.ComponentContext
import ru.shafran.common.details.edit.CustomerEditingHostComponent
import ru.shafran.common.details.info.CustomerInfoHostComponent
import ru.shafran.common.details.sessions.activation.SessionActivationHostComponent
import ru.shafran.common.details.sessions.use.SessionUseHostComponent
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class CustomerDetailsChildFactory(
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
    private val onEdit: (Customer) -> Unit,
    private val onUseSession: (Session) -> Unit,
    private val onActivateSession: (Customer.ActivatedCustomer) -> Unit,
) : (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child<Any?> {

    override fun invoke(
        configuration: CustomerDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): CustomerDetailsHost.Child<Any?> = when (configuration) {
        is CustomerDetailsHost.Configuration.CustomerInfo ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.EditCustomer ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.Hidden ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionActivation ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionUse ->
            configuration.create(componentContext)
    }

    private fun CustomerDetailsHost.Configuration.Hidden.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.Hidden
    }

    private fun CustomerDetailsHost.Configuration.SessionUse.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionUse(
            SessionUseHostComponent(
                componentContext = componentContext,
                session = session,
                onBack = onBack,
                onBackAndUpdate = onBackAndUpdate,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.SessionActivation.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionActivation(
            SessionActivationHostComponent(
                componentContext = componentContext,
                customer = customer,
                onBack = onBack,
                onBackAndUpdate = onBackAndUpdate,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.EditCustomer.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.EditCustomer(
            CustomerEditingHostComponent(
                componentContext = componentContext,
                customerId = customerId,
                onBack = onBack,
                onBackAndUpdate = onBackAndUpdate,
            )
        )
    }


    private fun CustomerDetailsHost.Configuration.CustomerInfo.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.CustomerInfo(
            CustomerInfoHostComponent(
                componentContext = componentContext,
                customerToken = customerToken,
                onBack = onBack,
                onEdit = { onEdit(it) },
                onUseSession = onUseSession,
                onActivateSession = { onActivateSession(it) },
            )
        )
    }


}

