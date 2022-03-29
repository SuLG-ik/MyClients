package ru.shafran.common.customers.details.search

import com.arkivanov.decompose.value.Value

interface CustomerSearchHost {

    val currentOption: Value<SearchOption>

    val searchByName: CustomerSearchByName

    val searchByPhone: CustomerSearchByPhone

    enum class SearchOption {
        NAME, PHONE,
    }

}