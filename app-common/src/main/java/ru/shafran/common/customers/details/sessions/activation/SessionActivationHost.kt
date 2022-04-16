package ru.shafran.common.customers.details.sessions.activation

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.Service

interface SessionActivationHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Empty : Configuration()

        @Parcelize
        object DetailsLoading : Configuration()

        @Parcelize
        object ActivationLoading : Configuration()


        @Parcelize
        data class Loaded(
            val customer: Customer.ActivatedCustomer,
            val employees: List<Employee>,
            val services: List<Service>,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading
        ) : Child()

        class Loaded(
            val component: SessionActivating,
        ) : Child()

    }

}