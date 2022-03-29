package ru.shafran.common.customers.details.search

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.FoundCustomerItem

sealed class FoundCustomerConfiguration : Parcelable {

    @Parcelize
    object EmptyInput : FoundCustomerConfiguration()

    @Parcelize
    object Loading : FoundCustomerConfiguration()

    @Parcelize
    object UnknownError : FoundCustomerConfiguration()


    @Parcelize
    class CustomersList(val customers: List<FoundCustomerItem>) :
        FoundCustomerConfiguration()

}


sealed class FoundCustomerChild {

    class EmptyInput(val component: ru.shafran.common.customers.details.search.EmptyInput) :
        FoundCustomerChild()

    class Loading(val component: ru.shafran.common.loading.Loading) :
        FoundCustomerChild()


    class CustomersList(val component: FoundCustomersList) :
        FoundCustomerChild()


    class Error(val component: ru.shafran.common.error.Error) : FoundCustomerChild()

}
