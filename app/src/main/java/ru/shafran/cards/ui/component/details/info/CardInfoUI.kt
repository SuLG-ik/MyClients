package ru.shafran.cards.ui.component.details.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.data.card.CardDescription

@Composable
fun CardInfoUI(component: CardInfo, modifier: Modifier) {
    Children(routerState = component.routerState) {
        when (val instance = it.instance) {
            is CardInfo.Child.Loading -> {
                Loading(component = instance.component, modifier = modifier)
            }
            is CardInfo.Child.Success -> {
                SuccessCard(component = instance.component, modifier = modifier)
            }
        }
    }
}

@Composable
fun Loading(component: Loading, modifier: Modifier) {
    LaunchedEffect(key1 = Unit, block = {
        component.onLoad()
    })
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(25.dp)
        )
    }
}

@Composable
fun SuccessCard(component: CardInfoLoaded, modifier: Modifier) {
    Column(modifier) {
        val card by component.currentCard.subscribeAsState()
        Text(text = "ID: ${card.id}")
        CardDescription(
            description = card.description,
            modifier = modifier,
        )
    }
}

@Composable
fun CardDescription(description: CardDescription, modifier: Modifier) {
    when (description) {
        is CardDescription.NewerUsed -> CardDescriptionNewerUsed(
            description = description,
            modifier = modifier
        )
        is CardDescription.Activated -> CardDescriptionActivated(
            description = description,
            modifier = modifier
        )
        is CardDescription.Deactivated -> CardDescriptionDeactivated(
            description = description,
            modifier = modifier
        )
    }
}

@Composable
fun CardDescriptionActivated(description: CardDescription.Activated, modifier: Modifier) {
    Column(modifier) {
        Text("Состояние: активирована")
        Text("Дата: ${description.activation.time}")
        Text("Услуга: ${description.activation.data.type.type}")
        Text("Последнее использование: ${description.lastUsage?.time}")
    }
}

@Composable
fun CardDescriptionDeactivated(description: CardDescription.Deactivated, modifier: Modifier) {
    Column(modifier) {
        Text("Состояние: не активирована")
        Text("Причина: ${description.deactivation.data.reason}")
        Text("Последняя услуга: ${description.activation.data.type.type}")
        Text("Последнее использование: ${description.lastUsage?.time ?: "ни разу"}")
    }
}

@Composable
fun CardDescriptionNewerUsed(description: CardDescription.NewerUsed, modifier: Modifier) {
    Column(modifier) {
        Text("Состояние: не активирована")
    }
}