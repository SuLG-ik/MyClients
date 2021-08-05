package ru.shafran.cards.ui.component.main

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface Main {

    val routerState: Value<RouterState<MainConfiguration, Child>>

    sealed class Child {
        class Camera(val camera: ru.shafran.cards.ui.component.camera.Camera): Child()
        class Splash(val splash: ru.shafran.cards.ui.component.splash.Splash): Child()
    }
}