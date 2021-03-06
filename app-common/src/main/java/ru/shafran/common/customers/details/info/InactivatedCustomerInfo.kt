package ru.shafran.common.customers.details.info

import ru.shafran.network.customers.data.Customer

interface InactivatedCustomerInfo {

    val customer: Customer.InactivatedCustomer

    fun onBack()

    fun onEdit()

}