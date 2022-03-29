package ru.shafran.common.customers.details.info

import ru.shafran.network.customers.data.Customer

interface PreloadedCustomerInfo {

    val customer: Customer.ActivatedCustomer

    fun onBack()

}