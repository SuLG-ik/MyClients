package ru.shafran.cards.ui.component.root

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.ui.component.cards.CardsUI
import ru.shafran.cards.ui.component.employees.EmployeesUI
import ru.shafran.cards.ui.component.services.ServicesUI
import ru.shafran.cards.ui.component.tickets.TicketsUI


@Composable
fun RootUI(component: Root, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            val routerState = component.routerState.subscribeAsState()
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .border(1.dp,
                        MaterialTheme.colors.onBackground.copy(0.12f),
                        MaterialTheme.shapes.medium),
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                contentColor = MaterialTheme.colors.primary,

                ) {
                for ((key, value) in RootConfiguration.children) {
                    BottomNavigationItem(
                        selected = routerState.value.activeChild.configuration == key,
                        onClick = { component.onNavigate(key) },
                        icon = {
                            Icon(painterResource(id = value.icon),
                                null,
                                modifier = Modifier.size(25.dp))
                        },
                        label = { Text(value.title) },
                        alwaysShowLabel = false,
                    )
                }
            }
        },
        modifier = modifier,
    ) { paddingValues ->
        Children(routerState = component.routerState) { child ->
            val defaultModifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            when (val instance = child.instance) {
                is Root.Child.Tickets ->
                    TicketsUI(component = instance.tickets, modifier = defaultModifier)
                is Root.Child.Cards ->
                    CardsUI(component = instance.cards, modifier = defaultModifier)
                is Root.Child.Employees ->
                    EmployeesUI(component = instance.employees, modifier = defaultModifier)
                is Root.Child.Services ->
                    ServicesUI(component = instance.services, modifier = defaultModifier)
            }
        }
    }
}