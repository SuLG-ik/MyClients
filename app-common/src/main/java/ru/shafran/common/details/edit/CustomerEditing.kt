package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.Customer

interface CustomerEditing {

    val customer: Customer.ActivatedCustomer

    val editor: CustomerEditor

}