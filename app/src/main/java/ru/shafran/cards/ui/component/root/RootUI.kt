package ru.shafran.cards.ui.component.root

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.R
import ru.shafran.cards.ui.component.cards.CardsUI
import ru.shafran.cards.ui.component.employees.EmployeesUI
import ru.shafran.cards.ui.component.history.HistoryUI


@Composable
fun RootUI(component: Root, modifier: Modifier = Modifier) {
    val routerState = component.routerState.subscribeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                val currentConfiguration = routerState.value.activeChild.configuration
                Crossfade(
                    targetState = RootConfiguration.children[currentConfiguration]?.title
                        ?: stringResource(id = R.string.app_name),
                    animationSpec = tween(250)
                ) {
                    Text(it, style = MaterialTheme.typography.h6)
                }
            }
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onBackground.copy(0.12f),
                        MaterialTheme.shapes.medium
                    ),
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                contentColor = MaterialTheme.colors.primary,
            ) {
                for ((key, value) in RootConfiguration.children) {
                    BottomNavigationItem(
                        selected = routerState.value.activeChild.configuration == key,
                        onClick = { component.onNavigate(key) },
                        icon = {
                            Icon(
                                painterResource(id = value.icon),
                                null,
                                modifier = Modifier.size(25.dp)
                            )
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
            RootChildren(instance = child.instance, modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues))
        }
    }
}

@Composable
fun RootChildren(instance: Root.Child, modifier: Modifier) {
    when (instance) {
        is Root.Child.History ->
            HistoryUI(component = instance.component, modifier = modifier)
        is Root.Child.Cards ->
            CardsUI(component = instance.component, modifier = modifier)
        is Root.Child.Employees ->
            EmployeesUI(component = instance.component, modifier = modifier)
    }
}