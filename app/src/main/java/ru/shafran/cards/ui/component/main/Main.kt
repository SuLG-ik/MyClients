package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface Main {

    val routerState: Value<RouterState<MainConfiguration, Child>>

    sealed class Child {
        class Root(val root: ru.shafran.cards.ui.component.root.Root): Child()
    }
}