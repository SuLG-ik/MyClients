package ru.shafran.ui.services.details.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import ru.shafran.common.services.details.edit.ServiceEditor
import ru.shafran.network.services.data.EditableServiceData
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedTextField

@Composable
fun ServiceEditorUI(
    component: ServiceEditor,
    modifier: Modifier,
) {
    val data =
        rememberSaveable(component.data) { mutableStateOf(component.data ?: EditableServiceData()) }
    ServiceEditor(
        data = data.value,
        onValueChanged = { data.value = it },
        onApply = component.onEdit,
        modifier = modifier,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ServiceEditor(
    data: EditableServiceData,
    onValueChanged: (EditableServiceData) -> Unit,
    onApply: (EditableServiceData) -> Unit,
    modifier: Modifier,
) {
    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = data.title,
            onValueChange = {
                if (it.length >= 64) return@OutlinedTextField
                onValueChanged(data.copy(title = it))
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
            value = data.description,
            onValueChange = {
                if (it.length >= 10000) return@OutlinedTextField
                onValueChanged(data.copy(description = it))
            },
            label = { Text(stringResource(R.string.services_create_description)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) },
                onDone = {
                    focus.clearFocus()
                    keyboard?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            enabled = data.title.isNotEmpty(),
            onClick = {
                onApply(data)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_apply))
        }
    }
}