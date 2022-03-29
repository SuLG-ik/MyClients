package ru.shafran.common.customers.details.generator

import ru.shafran.common.customers.details.edit.CustomerEditor
import ru.shafran.network.customers.data.CreateCustomerRequest

interface CardGenerator {

    val editor: CustomerEditor

    fun onGenerate(request: CreateCustomerRequest)

}