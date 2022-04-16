package ru.shafran.ui.employees.details.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.shafran.common.employees.details.create.EmployeeCreator
import ru.shafran.network.Gender
import ru.shafran.network.employees.data.CreateEmployeeRequestData
import ru.shafran.ui.R
import ru.shafran.ui.view.FloatingGenderSelector
import ru.shafran.ui.view.OutlinedTextField

@Composable
fun EmployeeCreatorUI(
    component: EmployeeCreator,
    modifier: Modifier,
) {
    val data = remember {
        mutableStateOf(component.data ?: CreateEmployeeRequestData("", Gender.UNKNOWN, ""))
    }
    EmployeeCreator(
        data.value,
        onUpdateData = { data.value = it },
        onApply = component.onApply,
        modifier = modifier,
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmployeeCreator(
    data: CreateEmployeeRequestData,
    onUpdateData: (CreateEmployeeRequestData) -> Unit,
    onApply: (CreateEmployeeRequestData) -> Unit,
    modifier: Modifier,
) {
    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = data.name,
                onValueChange = {
                    if (it.length >= 64) return@OutlinedTextField
                    onUpdateData(data.copy(name = it))
                },
                label = { Text(stringResource(R.string.services_create_title)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focus.moveFocus(FocusDirection.Down) },
                    onDone = {
                        focus.clearFocus()
                        keyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            )
            FloatingGenderSelector(
                selectedGender = data.gender,
                onSelect = { onUpdateData(data.copy(gender = it)) },
            )
        }
        OutlinedTextField(
            value = data.description,
            onValueChange = {
                if (it.length >= 1000) return@OutlinedTextField
                onUpdateData(data.copy(description = it))
            },
            label = { Text(stringResource(R.string.services_create_title)) },
            maxLines = 4,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) },
                onDone = {
                    focus.clearFocus()
                    keyboard?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false)
        )
        OutlinedButton(
            enabled = data.name.length >= 2,
            onClick = {
                onApply(data)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.employee_create_apply))
        }
    }
}