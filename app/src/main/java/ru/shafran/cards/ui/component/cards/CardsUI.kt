package ru.shafran.cards.ui.component.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.R
import ru.shafran.cards.ui.component.camera.CameraUI
import ru.shafran.cards.ui.component.details.CardDetailsUI
import ru.shafran.cards.ui.component.usages.UsagesListUI

@Composable
fun CardsUI(component: Cards, modifier: Modifier = Modifier) {
    val currentState by component.routerState.subscribeAsState()
    CardDetailsUI(component = component.details, modifier) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                if (currentState.activeChild.configuration !is CardsConfiguration.Camera)
                    FloatingActionButton(
                        onClick = component::onTakeCard,
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.primary

                    ) {
                        Icon(
                            painterResource(id = R.drawable.logo_take_card),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
            }
        ) { paddingValues ->
            Children(routerState = currentState) {
                when (val instance = it.instance) {
                    is Cards.Child.Camera ->
                        CameraUI(
                            component = instance.camera,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    is Cards.Child.UsagesList ->
                        UsagesListUI(
                            component = instance.usagesList,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        )
                }
            }
        }
    }
}