package ru.shafran.ui.services.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.services.picker.service.ServiceSelector
import ru.shafran.common.services.picker.service.ServicesListSelector
import ru.shafran.network.services.data.Service
import ru.shafran.ui.R
import ru.shafran.ui.loading.LoadingUI
import ru.shafran.ui.view.OutlinedTitledDialog

@Composable
internal fun ServiceSelectorUI(component: ServiceSelector, modifier: Modifier) {
    Children(routerState = component.routerState) {
        ServicesSelectorNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
private fun ServicesSelectorNavHost(child: ServiceSelector.Child, modifier: Modifier) {
    when (child) {
        is ServiceSelector.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is ServiceSelector.Child.ServicesList ->
            ServicesListUI(component = child.component, modifier = modifier)
    }
}

@Composable
private fun ServicesListUI(component: ServicesListSelector, modifier: Modifier) {
    ServicesList(
        selectedService = component.selectedConfiguration?.serviceId,
        services = component.services,
        onSelect = component::onSelect,
        modifier = modifier,
    )
}


@Composable
private fun ServicesList(
    selectedService: String?,
    services: List<Service>,
    onSelect: (Service) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customer_session_activation_services_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        val state = rememberScrollState()
        LaunchedEffect(key1 = selectedService, block = {
            if (selectedService != null) {
//                state.scrollToItem(services.indexOfFirst { it.id == selectedService })
            }
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state),
        ) {
            (services).forEach { service ->
                Box(modifier = Modifier
                    .clickable { onSelect(service) }) {
                    Service(
                        service = service,
                        isSelected = selectedService == service.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Service(
    service: Service,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            service.data.info.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
