package ru.shafran.cards.ui.component.root

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface Root {


    val routerState: Value<RouterState<RootConfiguration, Child>>

    fun onNavigate(configuration: RootConfiguration)

    sealed class Child {

        class History(val component: ru.shafran.cards.ui.component.history.History) : Child()

        class Employees(val component: ru.shafran.cards.ui.component.employees.Employees) : Child()

        class Cards(val component: ru.shafran.cards.ui.component.cards.Cards) : Child()

    }

}