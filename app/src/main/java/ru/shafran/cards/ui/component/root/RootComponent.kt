package ru.shafran.cards.ui.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.push
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.ui.component.cards.CardsComponent
import ru.shafran.cards.ui.component.employees.EmployeesComponent
import ru.shafran.cards.ui.component.services.ServicesComponent
import ru.shafran.cards.ui.component.tickets.TicketsComponent

class RootComponent(componentContext: ComponentContext) : Root,
    ComponentContext by componentContext {
    private val router = router<RootConfiguration, Root.Child>(
        initialConfiguration = RootConfiguration.Cards,
        handleBackButton = true,
        childFactory = this::createChild,
        key = "root_router",
    )

    private fun createChild(
        configuration: RootConfiguration,
        componentContext: ComponentContext,
    ): Root.Child {
        return when (configuration) {
            RootConfiguration.Cards -> Root.Child.Cards(CardsComponent(componentContext))
            RootConfiguration.Employees -> Root.Child.Employees(EmployeesComponent(componentContext))
            RootConfiguration.Services -> Root.Child.Services(ServicesComponent())
            RootConfiguration.Tickets -> Root.Child.Tickets(TicketsComponent())
        }
    }

    override val routerState: Value<RouterState<RootConfiguration, Root.Child>> = router.state

    override fun onNavigate(configuration: RootConfiguration) {
        router.push(configuration)
    }


}