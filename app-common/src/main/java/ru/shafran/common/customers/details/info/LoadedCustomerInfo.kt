package ru.shafran.common.customers.details.info

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

interface LoadedCustomerInfo {

    val customer: Customer.ActivatedCustomer

    val history: List<Session>

    val onBack: () -> Unit

    val onActivateSession: () -> Unit

    val onUseSession: (session: Session) -> Unit

    val onDeleteSession: (session: Session) -> Unit

    val onEdit : () -> Unit

    val onShareCard: () -> Unit

}