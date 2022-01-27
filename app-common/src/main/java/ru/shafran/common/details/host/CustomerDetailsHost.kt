package ru.shafran.common.details.host

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.details.edit.CustomerEditingHost
import ru.shafran.common.details.info.CustomerInfoHost
import ru.shafran.common.details.sessions.SessionActivationHost

interface CustomerDetailsHost {

    val isShown: Value<Boolean>

    fun onShowCustomer(token: String)

    fun onHide()

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Hidden : Configuration()

        @Parcelize
        data class CustomerInfo(
            val customerToken: String
        ): Configuration()

        @Parcelize
        data class EditCustomer(
            val customerId: String,
        ) : Configuration()

        @Parcelize
        data class SessionActivation(
            val customerId: String,
        ) : Configuration()

    }

    sealed class Child {

        object Hidden : Child()

        class CustomerInfo(
            val component: CustomerInfoHost,
        ): Child()

        class EditCustomer(
            val component: CustomerEditingHost,
        ) :Child()

        data class SessionActivation(
            val component: SessionActivationHost
        ) : Child()

    }

}