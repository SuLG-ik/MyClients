package ru.shafran.cards.ui.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.cards.ui.component.root.RootUI

@Composable
fun MainUI(component: Main, modifier: Modifier) {
    Box(modifier) {
        Children(routerState = component.routerState) {
            when (val instance = it.instance) {
                is Main.Child.Root -> RootUI(
                    component = instance.root,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}