package ru.shafran.common.customers.details.edit

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.Customer

interface CustomerEditingHost {

    val routerState: Value<RouterState<Configuration, Child>>


    sealed class Configuration : Parcelable {

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        data class Activating(
            val customer: Customer.InactivatedCustomer,
        ) : Configuration()

        @Parcelize
        data class Editing(
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

        @Parcelize
        class UnknownError() : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Activating(
            val component: CustomerActivating,
        ) : Child()

        class Editing(
            val component: CustomerEditing,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()

    }

}