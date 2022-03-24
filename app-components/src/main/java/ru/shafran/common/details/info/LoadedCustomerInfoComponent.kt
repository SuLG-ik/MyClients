package ru.shafran.common.details.info

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class LoadedCustomerInfoComponent(
    override val customer: Customer.ActivatedCustomer,
    override val history: List<Session>,
    private val onBack: () -> Unit,
    private val onEdit: () -> Unit,
    private val onActivateSession: () -> Unit,
    private val onUseSession: (Session) -> Unit,
) : LoadedCustomerInfo {

    override fun onBack() {
        onBack.invoke()
    }

    override fun onEdit() {
        onEdit.invoke()
    }

    override fun onActivateSession() {
        onActivateSession.invoke()
    }

    override fun onUseSession(session: Session) {
        onUseSession.invoke(session)
    }
}