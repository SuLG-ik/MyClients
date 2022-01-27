package ru.shafran.ui.details.edit

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.StaticDropdownMenu
import ru.shafran.ui.view.TitledDialog


@Composable
internal fun ServiceSelector(
    selectedConfiguration: ConfiguredService?,
    onSelect: (ConfiguredService) -> Unit,
    services: List<Service>,
    modifier: Modifier = Modifier,
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        OutlinedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded.value = true }
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                if (selectedConfiguration == null) {
                    EmptyServiceConfiguration(
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                } else {
                    ServiceConfiguration(
                        configuration = selectedConfiguration.configuration,
                        isSelected = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        }
        ServicesDropdown(
            isExpanded = isExpanded.value,
            onDismiss = { isExpanded.value = false },
            selectedConfiguration = selectedConfiguration,
            onSelect = onSelect,
            services = services,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(400.dp),
        )
    }
}

@Composable
private fun ServicesDropdown(
    isExpanded: Boolean,
    onDismiss: () -> Unit,
    selectedConfiguration: ConfiguredService?,
    onSelect: (ConfiguredService) -> Unit,
    services: List<Service>,
    modifier: Modifier = Modifier,
) {
    StaticDropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        val selectedService = remember(services, selectedConfiguration) {
            mutableStateOf(services.firstOrNull { it.id == selectedConfiguration?.serviceId })
        }
        Crossfade(
            selectedService.value,
            modifier = Modifier.fillMaxSize(),
        ) { service ->
            if (service == null) {
                ServicesList(
                    selectedService = selectedConfiguration?.serviceId,
                    onSelect = {
                        selectedService.value = it
                    },
                    services = services,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                )
            } else {
                ServiceConfigurationList(
                    service = service,
                    selectedConfigurationId = selectedConfiguration?.configuration?.id,
                    onSelect = onSelect,
                    onBack = { selectedService.value = null },
                    configurations = service.data.configurations,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                )
            }
        }
    }
}

@Composable
private fun EmptyServiceConfiguration(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(Icons.Outlined.Close, contentDescription = null)
        Text(stringResource(R.string.customer_session_activation_empty_service))
    }
}

@Composable
private fun ServicesList(
    selectedService: String?,
    services: List<Service>,
    onSelect: (Service) -> Unit,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = stringResource(R.string.customer_session_activation_services_title),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            services.forEach { service ->
                Box(modifier = Modifier
                    .fillMaxSize()
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
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun ServiceConfigurationList(
    service: Service,
    configurations: List<ServiceConfiguration>,
    onSelect: (ConfiguredService) -> Unit,
    onBack: () -> Unit,
    selectedConfigurationId: String?,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = service.data.info.title,
        onBackPressed = onBack,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            configurations.forEach { configuration ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onSelect(
                            ConfiguredService(
                                serviceId = service.id,
                                info = service.data.info,
                                image = service.data.image,
                                configuration = configuration,
                            )
                        )
                    }
                ) {
                    ServiceConfiguration(
                        configuration = configuration,
                        isSelected = selectedConfigurationId == configuration.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceConfiguration(
    configuration: ServiceConfiguration,
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
            configuration.title,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}