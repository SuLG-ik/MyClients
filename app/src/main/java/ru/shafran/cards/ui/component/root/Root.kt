package ru.shafran.cards.ui.component.root

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface Root {


    val routerState: Value<RouterState<RootConfiguration, Child>>

    fun onNavigate(configuration: RootConfiguration)

    sealed class Child {

        class Tickets(val tickets: ru.shafran.cards.ui.component.tickets.Tickets) : Child()

        class Employees(val employees: ru.shafran.cards.ui.component.employees.Employees) : Child()

        class Services(val services: ru.shafran.cards.ui.component.services.Services) : Child()

        class Cards(val cards: ru.shafran.cards.ui.component.cards.Cards) : Child()

    }

}