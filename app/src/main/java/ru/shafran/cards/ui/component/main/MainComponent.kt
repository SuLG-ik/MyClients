package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.ui.component.root.RootComponent

class MainComponent(
    componentContext: ComponentContext,
    private val onOpenSettings: () -> Unit,
) : Main,
    ComponentContext by componentContext {

    private val router = router<MainConfiguration, Main.Child>(
        initialConfiguration = MainConfiguration.Root,
        childFactory = this::createChild,
        handleBackButton = true
    )

    private fun createChild(
        configuration: MainConfiguration,
        componentContext: ComponentContext
    ): Main.Child =
        when(configuration) {
            is MainConfiguration.Root -> Main.Child.Root(RootComponent(componentContext, onOpenSettings = onOpenSettings))
        }


    override val routerState: Value<RouterState<MainConfiguration, Main.Child>> = router.state

}