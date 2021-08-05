package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.replaceCurrent
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.ui.component.camera.CameraComponent
import ru.shafran.cards.ui.component.splash.SplashComponent

class MainComponent(componentContext: ComponentContext) : Main,
    ComponentContext by componentContext {

    private val router = router<MainConfiguration, Main.Child>(
        initialConfiguration = MainConfiguration.Splash,
        childFactory = this::createChild
    )

    private fun createChild(
        configuration: MainConfiguration,
        componentContext: ComponentContext
    ): Main.Child =
        when(configuration) {
            is MainConfiguration.Splash -> Main.Child.Splash(SplashComponent(this::onSplashCompleted))
            is MainConfiguration.Camera -> Main.Child.Camera(CameraComponent(componentContext))
        }

    private fun onSplashCompleted() {
        router.replaceCurrent(MainConfiguration.Camera)
    }


    override val routerState: Value<RouterState<MainConfiguration, Main.Child>> = router.state

}