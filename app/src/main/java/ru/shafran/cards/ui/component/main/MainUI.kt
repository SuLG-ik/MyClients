package ru.shafran.cards.ui.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.cards.ui.component.camera.CameraUI
import ru.shafran.cards.ui.component.splash.SplashUI

@Composable
fun MainUI(component: Main, modifier: Modifier) {
    Box(modifier) {
        Children(routerState = component.routerState) {
            when (val instance = it.instance) {
                is Main.Child.Camera -> CameraUI(
                    component = instance.camera,
                    modifier = Modifier.fillMaxSize()
                )
                is Main.Child.Splash -> SplashUI(
                    component = instance.splash,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}