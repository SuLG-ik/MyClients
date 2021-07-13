package ru.shafran.cards.ui.component.splash

import kotlinx.coroutines.delay

class SplashComponent(private val onComplete: () -> Unit) : Splash {

    override suspend fun setup() {
        delay(250)
        onComplete()
    }
}