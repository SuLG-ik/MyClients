package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import ru.shafran.cards.ui.component.camera.CameraComponent
import ru.shafran.cards.ui.component.splash.SplashComponent

class MainComponent(componentContext: ComponentContext, coroutineScope: CoroutineScope) : Main,
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