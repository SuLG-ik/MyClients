package ru.shafran.common.customers.details.sessions.activation

import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.common.services.picker.ConfiguredServicePicker
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

interface SessionActivation {

    val customer: Customer.ActivatedCustomer

    val servicePicker: ConfiguredServicePicker

    val employeePicker: EmployeePicker

    fun onActivate(request: CreateSessionForCustomerRequest)

    fun onBack()

}