package ru.shafran.common.details.sessions

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.Service
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

internal class SessionActivationComponent(
    override val customer: Customer.ActivatedCustomer,
    override val services: List<Service>,
    override val employees: List<Employee>,
    private val onActivate: (CreateSessionForCustomerRequest) -> Unit,
    private val onBack: () -> Unit,
) : SessionActivation {
    override fun onActivate(request: CreateSessionForCustomerRequest) {
        onActivate.invoke(request)
    }

    override fun onBack() {
        onBack.invoke()
    }


}