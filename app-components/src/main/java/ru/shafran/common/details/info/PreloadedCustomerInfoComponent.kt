package ru.shafran.common.details.info

import ru.shafran.network.customers.data.Customer

internal class PreloadedCustomerInfoComponent(
    override val customer: Customer.ActivatedCustomer,
    private val onBack: () -> Unit,
) : PreloadedCustomerInfo {

    override fun onBack() {
        onBack.invoke()
    }

}