package ru.shafran.cards.ui.component.root

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.ui.component.cards.Cards
import ru.shafran.cards.ui.component.cards.CardsComponent
import ru.shafran.cards.ui.component.employees.Employees
import ru.shafran.cards.ui.component.employees.EmployeesComponent
import ru.shafran.cards.ui.component.history.History
import ru.shafran.cards.ui.component.history.HistoryComponent

class RootComponent(componentContext: ComponentContext) : Root,
    ComponentContext by componentContext {

    private val cards: Cards = CardsComponent(childContext("cards_component"))
    private val employees: Employees = EmployeesComponent(childContext("employees_component"))
    private val history: History = HistoryComponent(childContext("history_component"))

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
            RootConfiguration.Cards -> {
                Root.Child.Cards(cards)
            }
            RootConfiguration.Employees -> {
                Root.Child.Employees(employees)
            }
            RootConfiguration.History -> {
                Root.Child.History(history)
            }
        }
    }

    override val routerState: Value<RouterState<RootConfiguration, Root.Child>> = router.state


    override fun onNavigate(configuration: RootConfiguration) {
        router.push(configuration)
    }


}