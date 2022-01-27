package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.Customer

interface CustomerActivating {

    val customer: Customer.InactivatedCustomer

    val editor: CustomerEditor

}