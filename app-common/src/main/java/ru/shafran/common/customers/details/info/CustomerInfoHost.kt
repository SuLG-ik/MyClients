package ru.shafran.common.customers.details.info

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

interface CustomerInfoHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Empty : Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object CardNotFound : Configuration()

        @Parcelize
        object IllegalCard : Configuration()

        @Parcelize
        object CustomerNotFound : Configuration()

        @Parcelize
        object Unknown : Configuration()


        @Parcelize
        data class InactivatedLoaded(
            val customer: Customer.InactivatedCustomer,
        ) : Configuration()

        @Parcelize
        data class ActivatedPreloaded(
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

        @Parcelize
        data class ActivatedLoaded(
            val cardToken: String,
            val customer: Customer.ActivatedCustomer,
            val history: List<Session>,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Inactivated(
            val component: InactivatedCustomerInfo,
        ) : Child()

        class Preloaded(
            val component: PreloadedCustomerInfo,
        ) : Child()

        class Loaded(
            val component: LoadedCustomerInfo,
        ) : Child()


        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()

    }

}