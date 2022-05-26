package ru.shafran.ui.main

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.slide
import ru.shafran.common.main.Main
import ru.shafran.ui.auth.AuthUI
import ru.shafran.ui.companies.CompaniesSelectorUI
import ru.shafran.ui.root.RootUI

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainUI(component: Main, modifier: Modifier) {
    Children(routerState = component.routerState,
        animation = childAnimation(
            fade(tween(500)) +
                    slide(tween(600)) +
                    scale(tween(250))),
        content = {
            MainNavHost(child = it.instance, modifier = modifier)
        }
    )
}

@Composable
private fun MainNavHost(
    child: Main.Child,
    modifier: Modifier,
) {
    when (child) {
        is Main.Child.Application ->
            RootUI(component = child.component, modifier = modifier)
        is Main.Child.Auth ->
            AuthUI(component = child.component, modifier = modifier)
        is Main.Child.CompanySelector ->
            CompaniesSelectorUI(component = child.component, modifier = modifier)
        is Main.Child.Splash -> {}
    }
}