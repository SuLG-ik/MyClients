package ru.shafran.common.customers.details.generator

import ru.shafran.network.customers.data.Customer

interface CardSender {

    val token: String

    val customer: Customer.ActivatedCustomer

    fun onProfile()

    fun onShare()

}