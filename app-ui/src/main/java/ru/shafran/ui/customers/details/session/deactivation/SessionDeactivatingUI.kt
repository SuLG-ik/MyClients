package ru.shafran.ui.customers.details.session.deactivation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.common.customers.details.sessions.deactivation.SessionDeactivating
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.session.data.SessionManualDeactivationReason
import ru.shafran.ui.R
import ru.shafran.ui.customers.details.info.SessionItemHeader
import ru.shafran.ui.employees.picker.FloatingEmployeePickerUI
import ru.shafran.ui.view.FloatingEnumSelector
import ru.shafran.ui.view.MaterialDivider
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.OutlinedTextField
import ru.shafran.ui.view.TitledDialog

@Composable
fun SessionDeactivatingUI(component: SessionDeactivating, modifier: Modifier) {
    TitledDialog(
        title = {
            Text(
                stringResource(
                    id = R.string.sessions_deactivating_title,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = component.onBack,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
                SessionItemHeader(
                    session = component.session,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
            MaterialDivider(modifier = Modifier.fillMaxWidth())
            SessionDeactivating(
                employeePicker = component.employeePicker,
                onApply = component.onApply,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}


@Composable
fun SessionDeactivating(
    employeePicker: EmployeePicker,
    onApply: (DeactivateSessionRequestData) -> Unit,
    modifier: Modifier,
) {
    val reason = rememberSaveable { mutableStateOf(SessionManualDeactivationReason.UNKNOWN) }
    val selectedEmployee = employeePicker.selectedEmployee.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val note = rememberSaveable { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            FloatingEmployeePickerUI(
                component = employeePicker,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false),
            )
            FloatingEnumSelector(
                value = reason.value,
                onValueChanged = { reason.value = it },
                title = {
                    Text(
                        stringResource(id = R.string.sessions_deactivating_reason_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                iconFactory = SessionManualDeactivationReason::iconResource,
                labelFactory = SessionManualDeactivationReason::stringResource,
                modifier = Modifier.size(55.dp)
            )
        }
        OutlinedTextField(
            value = note.value,
            onValueChange = { note.value = it },
            label = { Text(text = stringResource(R.string.session_deactivating_note)) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(
            onClick = {
                val employee = selectedEmployee.value
                if (employee != null) {
                    onApply(DeactivateSessionRequestData(
                        employeeId = employee.id,
                        reason = reason.value,
                        note = note.value,
                    ))
                }

            },
            enabled = selectedEmployee.value != null,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.session_deactiving_apply))
        }
    }
}


val SessionManualDeactivationReason.stringResource: Int
    @StringRes
    get() = when (this) {
        SessionManualDeactivationReason.UNKNOWN -> R.string.session_deactivating_reason_unknown
        SessionManualDeactivationReason.MISS_CLICK -> R.string.session_deactivating_reason_miss_click
        SessionManualDeactivationReason.MONEY_BACK -> R.string.session_deactivating_reason_money_back
    }


private val SessionManualDeactivationReason.iconResource: Int
    @DrawableRes
    get() = when (this) {
        SessionManualDeactivationReason.UNKNOWN -> R.drawable.unknown
        SessionManualDeactivationReason.MISS_CLICK -> R.drawable.click
        SessionManualDeactivationReason.MONEY_BACK -> R.drawable.money_back
    }
