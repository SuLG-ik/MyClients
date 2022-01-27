package ru.shafran.ui.details.sessions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.details.sessions.SessionActivation
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.session.data.CreateServiceSessionForCustomerRequestData
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.ServiceConfigurationReference
import ru.shafran.ui.R
import ru.shafran.ui.details.edit.EmployeesSelector
import ru.shafran.ui.details.edit.ServiceSelector
import ru.shafran.ui.view.TitledDialog

@Composable
fun SessionActivationUI(
    component: SessionActivation,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = stringResource(
            id = R.string.customer_session_activation_title,
            component.customer.data.name,
        ),
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        SessionActivation(
            customer = component.customer,
            employees = component.employees,
            services = component.services,
            onActivate = component::onActivate,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SessionActivation(
    customer: Customer.ActivatedCustomer,
    employees: List<Employee>,
    services: List<Service>,
    onActivate: (CreateSessionForCustomerRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedConfiguration = remember { mutableStateOf<ConfiguredService?>(null) }
    val selectedEmployee = remember { mutableStateOf<Employee?>(null) }
    val remark = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        ServiceSelector(
            selectedConfiguration = selectedConfiguration.value,
            onSelect = { selectedConfiguration.value = it },
            services = services,
            modifier = Modifier.fillMaxWidth(),
        )
        EmployeesSelector(
            selectedEmployee = selectedEmployee.value,
            onSelect = { selectedEmployee.value = it },
            employees = employees,
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
            configuration = selectedConfiguration.value,
            employee = selectedEmployee.value,
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