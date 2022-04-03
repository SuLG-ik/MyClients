package ru.shafran.ui.services.details.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.services.details.info.LoadedServiceInfo
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.ui.R
import ru.shafran.ui.view.MaterialDivider
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.TitledDialog

@Composable
fun LoadedServiceInfoUI(component: LoadedServiceInfo, modifier: Modifier) {
    LoadedServiceInfo(
        service = component.service,
        modifier = modifier.verticalScroll(rememberScrollState()),
    )
}


@Composable
fun LoadedServiceInfo(
    service: Service,
    onEdit: (() -> Unit)? = null,
    modifier: Modifier,
) {
    TitledDialog(
        title = {
            Icon(painterResource(id = R.drawable.service_logo),
                contentDescription = null,
                modifier = Modifier.size(50.dp))
            Text(text = service.data.info.title, style = MaterialTheme.typography.headlineLarge)
        },
        modifier = modifier,
    ) {
        ServiceInfoHeader(
            service = service,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ServiceInfoHeader(
    service: Service,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(R.string.services_item_description),
            style = MaterialTheme.typography.titleLarge,
        )
        ServiceDescription(
            service = service,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            stringResource(R.string.services_item_configurations),
            style = MaterialTheme.typography.titleLarge
        )
        ServiceConfigurationsList(
            service = service,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ServiceDescription(service: Service, modifier: Modifier) {
    OutlinedSurface(
        modifier = modifier
    ) {
        if (service.data.info.description.isBlank()) {
            EmptyDescription(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp))
        } else {
            Description(
                service.data.info.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    }
}

@Composable
private fun Description(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
private fun EmptyDescription(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(id = R.drawable.empty_history), contentDescription = null)
        Text(
            stringResource(R.string.services_item_description_empty),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ServiceConfigurationsList(service: Service, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        service.data.configurations.forEach {
            ServiceConfiguration(it, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ServiceConfiguration(configuration: ServiceConfiguration, modifier: Modifier) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }
    OutlinedSurface(onClick = { isExpanded.value = !isExpanded.value }, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ServiceConfigurationHeader(
                configuration = configuration,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = isExpanded.value) {
                MaterialDivider(modifier = Modifier.fillMaxWidth())
                ServiceConfigurationData(
                    configuration = configuration,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ServiceConfigurationHeader(configuration: ServiceConfiguration, modifier: Modifier) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(id = R.drawable.selection),
            contentDescription = null,
            modifier = Modifier.size(50.dp))
        Text(configuration.title, style = MaterialTheme.typography.titleLarge)
    }
}


@Composable
fun ServiceConfigurationData(configuration: ServiceConfiguration, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier,
    ) {
        Row(
            modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painterResource(id = R.drawable.amount),
                contentDescription = null,
                modifier = Modifier.size(25.dp))
            Text("${configuration.amount}", style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(painterResource(id = R.drawable.cost),
                contentDescription = null,
                modifier = Modifier.size(25.dp))
            Text("${configuration.cost}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}