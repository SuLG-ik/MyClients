package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.CustomerData

interface CustomerEditor {

    val data: CustomerData?

    fun onEdit(data: CustomerData)

}