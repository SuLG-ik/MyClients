package ru.shafran.common.customers.details.host

import com.arkivanov.decompose.ComponentContext
import ru.shafran.common.customers.details.edit.CustomerEditingHostComponent
import ru.shafran.common.customers.details.generator.CardGeneratorHostComponent
import ru.shafran.common.customers.details.generator.CardSenderComponent
import ru.shafran.common.customers.details.info.CustomerInfoHostComponent
import ru.shafran.common.customers.details.search.CustomerSearchHostComponent
import ru.shafran.common.customers.details.sessions.activation.SessionActivationHostComponent
import ru.shafran.common.customers.details.sessions.deactivation.SessionDeactivationHostComponent
import ru.shafran.common.customers.details.sessions.use.SessionUsageHostComponent
import ru.shafran.common.sessions.stats.AllSessionsStatsHostComponent
import ru.shafran.common.utils.Share
import ru.shafran.network.companies.data.Company
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class CustomerDetailsChildFactory(
    private val company: Company,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
    private val onEdit: (Customer) -> Unit,
    private val onUseSession: (Session) -> Unit,
    private val onActivateSession: (Customer.ActivatedCustomer) -> Unit,
    private val onProfile: (Customer.ActivatedCustomer) -> Unit,
    private val onShareCard: (String, Customer.ActivatedCustomer) -> Unit,
    private val share: Share,
    private val onDeleteSession: (Session) -> Unit,
) : (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child<Any?> {

    override fun invoke(
        configuration: CustomerDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): CustomerDetailsHost.Child<Any?> = when (configuration) {
        is CustomerDetailsHost.Configuration.CustomerInfoByToken ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.CustomerInfoById ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.EditCustomer ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.Hidden ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionActivation ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionUse ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.GenerateCard ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.ShareCard ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.Search ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionDeactivation ->
            configuration.create(componentContext)
        is CustomerDetailsHost.Configuration.SessionStats ->
            configuration.create(componentContext)
    }

    private fun CustomerDetailsHost.Configuration.SessionStats.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionsStats(
            AllSessionsStatsHostComponent(
                componentContext = componentContext,
                company = company,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.SessionDeactivation.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionDeactivate(
            SessionDeactivationHostComponent(
                componentContext = componentContext,
                session = session,
                onBack = onBack,
                onBackAndUpdate = onBackAndUpdate,
                company = company,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.Search.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.CustomerSearch(CustomerSearchHostComponent(
            componentContext = componentContext,
            onFind = onProfile,
            company = company
        ))
    }


    private fun CustomerDetailsHost.Configuration.GenerateCard.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.GenerateCard(CardGeneratorHostComponent(
            componentContext = componentContext,
            onProfile = onProfile,
            share = share,
            company = company,
        ))
    }

    private fun CustomerDetailsHost.Configuration.Hidden.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.Hidden
    }

    private fun CustomerDetailsHost.Configuration.SessionUse.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionUse(
            SessionUsageHostComponent(
                componentContext = componentContext,
                session = session,
                onBack = onBack,
                onBackAndUpdate = onBackAndUpdate,
                company = company,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.SessionActivation.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.SessionActivation(
            SessionActivationHostComponent(
                componentContext = componentContext,
                company = company,
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


    private fun CustomerDetailsHost.Configuration.CustomerInfoByToken.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.CustomerInfo(
            CustomerInfoHostComponent(
                componentContext = componentContext,
                customerToken = customerToken,
                onBack = onBack,
                onEdit = { onEdit(it) },
                onUseSession = onUseSession,
                onDeleteSession = onDeleteSession,
                onActivateSession = { onActivateSession(it) },
                onShareCard = onShareCard,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.CustomerInfoById.create(componentContext: ComponentContext): CustomerDetailsHost.Child<Any?> {
        return CustomerDetailsHost.Child.CustomerInfo(
            CustomerInfoHostComponent(
                componentContext = componentContext,
                customerId = customerId,
                onBack = onBack,
                onEdit = { onEdit(it) },
                onUseSession = onUseSession,
                onDeleteSession = onDeleteSession,
                onActivateSession = { onActivateSession(it) },
                onShareCard = onShareCard,
            )
        )
    }

    private fun CustomerDetailsHost.Configuration.ShareCard.create(componentContext: ComponentContext): CustomerDetailsHost.Child.CardSender {
        return CustomerDetailsHost.Child.CardSender(
            CardSenderComponent(
                componentContext = componentContext,
                token = cardToken,
                customer = customer,
                share = share,
                onProfile = onProfile,
            )
        )
    }


}

