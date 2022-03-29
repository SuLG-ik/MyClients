package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.Customer

interface CustomerEditing {

    fun onBack()

    val customer: Customer.ActivatedCustomer

    val editor: CustomerEditor

}