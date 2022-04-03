package ru.shafran.common.customers.details.host

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.customers.details.edit.CustomerEditingHost
import ru.shafran.common.customers.details.generator.CardGeneratorHost
import ru.shafran.common.customers.details.info.CustomerInfoHost
import ru.shafran.common.customers.details.search.CustomerSearchHost
import ru.shafran.common.customers.details.sessions.activation.SessionActivationHost
import ru.shafran.common.customers.details.sessions.use.SessionUseHost
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

interface CustomerDetailsHost {

    val isShown: Value<Boolean>

    val onHide: () -> Unit

    val onGenerateCustomer: () -> Unit

    val onSearch: () -> Unit

    val onShowCustomerByCardToken: (String) -> Unit

    val onShowCustomerById: (String) -> Unit

    val onShowCustomer: (Customer.ActivatedCustomer) -> Unit

    val routerState: Value<RouterState<Configuration, Child<Any?>>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Hidden : Configuration()

        @Parcelize
        data class CustomerInfoByToken(
            val customerToken: String,
        ) : Configuration()

        @Parcelize
        data class CustomerInfoById(
            val customerId: String,
        ) : Configuration()

        @Parcelize
        data class EditCustomer(
            val customerId: String,
        ) : Configuration()

        @Parcelize
        data class SessionActivation(
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

        @Parcelize
        data class SessionUse(
            val session: Session,
        ) : Configuration()

        @Parcelize
        class GenerateCard : Configuration()

        @Parcelize
        class ShareCard(
            val cardToken: String,
            val customer: Customer.ActivatedCustomer,
        ) : Configuration()

        @Parcelize
        object Search : Configuration()

    }

    sealed class Child<out T> {

        abstract val component: T

        object Hidden : Child<Nothing?>() {
            override val component: Nothing?
                get() = null
        }

        class CustomerInfo(
            override val component: CustomerInfoHost,
        ) : Child<CustomerInfoHost>()

        class EditCustomer(
            override val component: CustomerEditingHost,
        ) : Child<CustomerEditingHost>()

        data class SessionActivation(
            override val component: SessionActivationHost,
        ) : Child<SessionActivationHost>()


        class SessionUse(
            override val component: SessionUseHost,
        ) : Child<SessionUseHost>()

        class GenerateCard(
            override val component: CardGeneratorHost,
        ) : Child<CardGeneratorHost>()

        class CardSender(
            override val component: ru.shafran.common.customers.details.generator.CardSender,
        ) : Child<ru.shafran.common.customers.details.generator.CardSender>()

        data class CustomerSearch(
            override val component: CustomerSearchHost,
        ) : Child<CustomerSearchHost>()

    }


}