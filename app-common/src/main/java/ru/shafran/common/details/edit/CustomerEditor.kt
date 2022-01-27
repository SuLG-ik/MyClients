package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.CustomerData

interface CustomerEditor {

    val data: CustomerData?

    fun onBack()

    fun onEdit(data: CustomerData)

}