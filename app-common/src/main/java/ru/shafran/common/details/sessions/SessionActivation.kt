package ru.shafran.common.details.sessions

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.Service
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

interface SessionActivation {

    val customer: Customer.ActivatedCustomer

    val services: List<Service>

    val employees: List<Employee>

    fun onActivate(request: CreateSessionForCustomerRequest)

    fun onBack()

}