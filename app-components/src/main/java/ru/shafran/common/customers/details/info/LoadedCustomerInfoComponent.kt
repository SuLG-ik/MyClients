package ru.shafran.common.customers.details.info

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class LoadedCustomerInfoComponent(
    override val customer: Customer.ActivatedCustomer,
    override val history: List<Session>,
    override val onBack: () -> Unit,
    override val onActivateSession: () -> Unit,
    override val onUseSession: (session: Session) -> Unit,
    override val onDeleteSession: (session: Session) -> Unit,
    override val onEdit: () -> Unit,
    override val onShareCard: () -> Unit,
) : LoadedCustomerInfo