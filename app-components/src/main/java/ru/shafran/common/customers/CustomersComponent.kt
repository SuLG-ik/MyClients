package ru.shafran.common.customers

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.sessions.SessionsHistoryListHostComponent
import ru.shafran.common.utils.Share

class CustomersComponent(
    componentContext: ComponentContext,
    private val share: Share,
) : Customers,
    ComponentContext by componentContext {

    private val router = router(
        initialConfiguration = Customers.Configuration.SessionsHistory,
        childFactory = this::createChild,
        key = "customers",
    )

    private fun createChild(
        configuration: Customers.Configuration,
        componentContext: ComponentContext,
    ): Customers.Child {
        return when (configuration) {
            Customers.Configuration.SessionsHistory ->
                Customers.Child.SessionsHistory(
                    SessionsHistoryListHostComponent(
                        componentContext,
                        share
                    )

                )
        }
    }

    override val routerState: Value<RouterState<Customers.Configuration, Customers.Child>>
        get() = router.state
}