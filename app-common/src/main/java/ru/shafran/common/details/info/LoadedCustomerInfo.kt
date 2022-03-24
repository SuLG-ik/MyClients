package ru.shafran.common.details.info

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

interface LoadedCustomerInfo {

    val customer: Customer.ActivatedCustomer

    val history: List<Session>

    fun onBack()

    fun onActivateSession()

    fun onUseSession(session: Session)

    fun onEdit()

}