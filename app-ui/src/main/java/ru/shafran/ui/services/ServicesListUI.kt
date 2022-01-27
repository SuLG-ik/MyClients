package ru.shafran.ui.services

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.common.services.list.ServicesList
import ru.shafran.common.services.list.ServicesListHost
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.ui.loading.LoadingUI
import ru.shafran.ui.view.OutlinedSurface

@Composable
internal fun ServicesListHostUI(component: ServicesListHost, modifier: Modifier) {
    Children(routerState = component.routerState, modifier = modifier) {
        ServicesListNavHost(child = it.instance, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun ServicesListNavHost(child: ServicesListHost.Child, modifier: Modifier) {
    when (child) {
        is ServicesListHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is ServicesListHost.Child.ServicesList ->
            ServicesListUI(child.component, modifier = modifier)
    }
}

@Composable
private fun ServicesListUI(component: ServicesList, modifier: Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { component.onCreateService() }) {
                Icon(Icons.Outlined.Add, contentDescription = null)
            }
        },
        modifier = modifier,
    ) {
        ServicesList(
            services = component.services,
            onSelect = component::onSelectService,
            onRefresh = component::onLoading,
            modifier = Modifier.fillMaxSize(),
        )
    }

}

@Composable
fun ServicesList(
    services: List<Service>,
    onSelect: (Service) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(
        state = state,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(services) {
                Service(
                    service = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(it) }
                )
            }
        }
    }
}

@Composable
fun Service(service: Service, modifier: Modifier = Modifier) {
    OutlinedSurface(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(Icons.Outlined.Info, contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    service.data.info.title,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                val scroll = rememberScrollState()
                Row(modifier = Modifier.horizontalScroll(scroll), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    service.data.configurations.forEach {
                        Configuration(configuration = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun Configuration(configuration: ServiceConfiguration, modifier: Modifier = Modifier) {
    OutlinedSurface(modifier) {
        Text(
            text = configuration.title,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(5.dp),
        )
    }
}