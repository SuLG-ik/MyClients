package ru.shafran.ui.customers.details.session.activation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import ru.shafran.common.customers.details.sessions.activation.SessionActivation
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.common.loading.Loading
import ru.shafran.common.services.picker.ConfiguredServicePicker
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.session.data.CreateServiceSessionForCustomerRequestData
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.ServiceConfigurationReference
import ru.shafran.ui.R
import ru.shafran.ui.employees.picker.FloatingEmployeePickerUI
import ru.shafran.ui.services.picker.FloatingServicePickerUI
import ru.shafran.ui.view.OutlinedTextField
import ru.shafran.ui.view.PlaceholderTextField
import ru.shafran.ui.view.OutlinedTitledDialog

@Composable
fun SessionActivationPlaceholderUI(
    component: Loading,
    modifier: Modifier = Modifier,
) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(id = component.message),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade())
            )
        },
        onBackPressed = {},
        modifier = modifier,
    ) {
        SessionActivationPlaceholderUI(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun SessionActivationPlaceholderUI(modifier: Modifier) {
    val remark = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PlaceholderTextField(
            modifier = Modifier.fillMaxWidth(),
        )
        PlaceholderTextField(
            modifier = Modifier.fillMaxWidth(),
        )
        PlaceholderTextField(
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Подключение не доступно", modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade()))
        }
    }
}

@Composable
fun SessionActivationUI(
    component: SessionActivation,
    modifier: Modifier = Modifier,
) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(
                    id = R.string.customer_session_activation_title,
                    component.customer.data.name,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        SessionActivationUI(
            customer = component.customer,
            servicePicker = component.servicePicker,
            employeePicker = component.employeePicker,
            onActivate = component::onActivate,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SessionActivationUI(
    customer: Customer.ActivatedCustomer,
    servicePicker: ConfiguredServicePicker,
    employeePicker: EmployeePicker,
    onActivate: (CreateSessionForCustomerRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    val remark = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        FloatingServicePickerUI(
            component = servicePicker,
            modifier = Modifier.fillMaxWidth(),
        )
        FloatingEmployeePickerUI(
            component = employeePicker,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = remark.value,
            onValueChange = { remark.value = it },
            label = { Text(stringResource(R.string.customer_session_activation_remark)) },
            maxLines = 5,
            modifier = Modifier.fillMaxWidth()
        )
        SessionActivationButton(
            customer = customer,
            configuration = servicePicker.selectedConfiguration.collectAsState().value,
            employee = employeePicker.selectedEmployee.collectAsState().value,
            remark = remark.value,
            onActivate = onActivate,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Composable
private fun SessionActivationButton(
    customer: Customer,
    configuration: ConfiguredService?,
    employee: Employee?,
    remark: String?,
    onActivate: (CreateSessionForCustomerRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = {
            if (configuration != null && employee != null)
                onActivate(
                    CreateSessionForCustomerRequest(
                        configuration = ServiceConfigurationReference(
                            serviceId = configuration.serviceId,
                            configurationId = configuration.configuration.id,
                        ),
                        employeeId = employee.id,
                        customerId = customer.id,
                        data = CreateServiceSessionForCustomerRequestData(
                            remark = remark
                        )
                    )
                )
        },
        enabled = configuration != null && employee != null,
        modifier = modifier,
    ) {
        if (configuration != null) {
            Text(
                stringResource(
                    id = R.string.customer_session_activation_continue,
                    configuration.info.title,
                )
            )
        } else {
            Text(stringResource(R.string.customer_session_activation_empty_continue))
        }
    }
}