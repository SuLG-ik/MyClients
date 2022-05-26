package ru.shafran.common.customers.details.generator

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.CreateCustomersRequest
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditableCustomerData

interface CardGeneratorHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        class CardGenerator(
            val data: EditableCustomerData?,
        ) : Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        class CardSender(
            val token: String,
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

        @Parcelize
        class UnknownError(
            val data: CreateCustomersRequest,
        ) : Configuration()

    }

    sealed class Child {
        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class CardGenerator(val component: ru.shafran.common.customers.details.generator.CardGenerator) :
            Child()

        class CardSender(val component: ru.shafran.common.customers.details.generator.CardSender) :
            Child()

        class Error(val component: ru.shafran.common.error.Error) : Child()

    }

}