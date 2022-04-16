package ru.shafran.ui.services.details.create

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.shafran.common.services.details.create.ServiceConfigurationCreating
import ru.shafran.common.services.details.create.ServiceConfigurationCreator
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedTextField
import ru.shafran.ui.view.TitledDialog

@Composable
fun ServiceConfigurationCreatingUI(
    component: ServiceConfigurationCreating,
    modifier: Modifier,
) {
    TitledDialog(
        title = { Text("Добавить конфигурацию") },
        onBackPressed = component.onBack,
        modifier = modifier,
    ) {
        ServiceConfigurationCreatorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ServiceConfigurationCreatorUI(
    component: ServiceConfigurationCreator,
    modifier: Modifier,
) {
    ServiceConfigurationCreator(
        onApply = component.onApply,
        modifier = modifier,
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ServiceConfigurationCreator(
    onApply: (CreateConfigurationRequestData) -> Unit,
    modifier: Modifier,
) {
    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {

        val title = rememberSaveable { mutableStateOf("") }
        val description = rememberSaveable { mutableStateOf("") }
        val amount = rememberSaveable { mutableStateOf("") }
        val cost = rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = title.value,
            onValueChange = {
                if (it.length >= 64) return@OutlinedTextField
                title.value = it
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
        OutlinedTextField(
            value = description.value,
            onValueChange = {
                if (it.length >= 10000) return@OutlinedTextField
                description.value = it
            },
            label = { Text(stringResource(R.string.services_create_description)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) },
                onDone = {
                    focus.clearFocus()
                    keyboard?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = amount.value,
                onValueChange = {
                    if (it.length > 9) return@OutlinedTextField
                    amount.value = it.filter(Char::isDigit)
                },
                label = { Text(stringResource(R.string.services_create_amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focus.moveFocus(FocusDirection.Right) },
                    onDone = {
                        focus.clearFocus()
                        keyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            )
            OutlinedTextField(
                value = cost.value,
                onValueChange = {
                    if (it.length > 9) return@OutlinedTextField
                    cost.value = it.filter(Char::isDigit)
                },
                label = { Text(stringResource(R.string.services_create_cost)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
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
        }
        OutlinedButton(
            enabled = title.value.length >= 2 && amount.value.toIntOrDefault() > 0,
            onClick = {
                onApply(
                    CreateConfigurationRequestData(
                        title = title.value.trim(),
                        description = title.value.trim(),
                        cost = cost.value.toIntOrDefault(),
                        amount = amount.value.toIntOrDefault(),
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_apply))
        }
    }
}

private fun String.toIntOrDefault(default: Int = 0): Int {
    return toIntOrNull() ?: default
}
