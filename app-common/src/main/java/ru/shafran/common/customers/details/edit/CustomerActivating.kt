package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.Customer

interface CustomerActivating {

    fun onBack()

    val customer: Customer.InactivatedCustomer

    val editor: CustomerEditor

}