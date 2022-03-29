package ru.shafran.ui.root

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.RouterState
import ru.shafran.common.root.Root
import ru.shafran.common.utils.NavigationItem
import ru.shafran.ui.R
import ru.shafran.ui.scanner.CustomerScannerUI
import ru.shafran.ui.services.ServicesUI
import ru.shafran.ui.view.FloatingBottomNavigation

private val children: Map<Root.Configuration, NavigationItem> by lazy {
    mapOf(
        Root.Configuration.CustomerScanner to NavigationItem(
            R.string.navigation_scanner_title,
            R.drawable.logo_cards,
        ),
        Root.Configuration.Services to NavigationItem(
            R.string.navigation_services_title,
            R.drawable.logo_services,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RootUI(component: Root, modifier: Modifier = Modifier) {
    val currentChild = component.routerState.subscribeAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.navigation_topbar_title),
                    )
                }
            )
        },
        bottomBar = {
            FloatingBottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                children.forEach {
                    RootNavigationItem(
                        currentConfiguration = currentChild.value.activeChild.configuration,
                        service = it.key,
                        item = it.value,
                        onClick = { component.onNavigate(it.key) },
                    )
                }
            }
        },
        modifier = modifier,
    ) {
        RootNavHost(
            value = currentChild.value,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        )
    }
}

@Composable
private fun RootNavHost(
    value: RouterState<Root.Configuration, Root.Child<Any?>>,
    modifier: Modifier,
) {
    Children(routerState = value) {
        when (val instance = it.instance) {
            is Root.Child.CustomerScanner ->
                CustomerScannerUI(component = instance.component, modifier = modifier)
            is Root.Child.Services ->
                ServicesUI(component = instance.component, modifier = modifier)
        }
    }
}


@Composable
private fun RowScope.RootNavigationItem(
    currentConfiguration: Root.Configuration,
    service: Root.Configuration,
    item: NavigationItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        selected = currentConfiguration == service,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = stringResource(id = item.label)
            )
        },
        modifier = modifier,
    )
}