package ru.shafran.common.details.info

import ru.shafran.common.details.info.InactivatedCustomerInfo
import ru.shafran.network.customers.data.Customer

internal class InactivatedCustomerInfoComponent(
    override val customer: Customer.InactivatedCustomer,
    private val onBack: () -> Unit,
    private val onEdit: () -> Unit,
) : InactivatedCustomerInfo {

    override fun onBack() {
        onBack.invoke()
    }

    override fun onEdit() {
        onEdit.invoke()
    }

}