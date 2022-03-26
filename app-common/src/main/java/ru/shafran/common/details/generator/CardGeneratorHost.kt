package ru.shafran.common.details.generator

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.Customer

interface CardGeneratorHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object CardGenerator : Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        class CardSender(
            val token: String,
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

    }

    sealed class Child {
        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class CardGenerator(val component: ru.shafran.common.details.generator.CardGenerator) :
            Child()

        class CardSender(val component: ru.shafran.common.details.generator.CardSender) : Child()

    }

}