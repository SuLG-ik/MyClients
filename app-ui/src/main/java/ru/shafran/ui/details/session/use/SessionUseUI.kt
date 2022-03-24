package ru.shafran.ui.details.session.use

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.shafran.common.details.sessions.use.SessionUse
import ru.shafran.ui.R
import ru.shafran.ui.employees.picker.FloatingEmployeePickerUI
import ru.shafran.ui.view.TitledDialog

@Composable
fun SessionUseUI(
    component: SessionUse,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = stringResource(
            id = R.string.customer_session_activation_title,
            component.session.activation.service.info.title,
        ),
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        val note = rememberSaveable { mutableStateOf("") }
        FloatingEmployeePickerUI(
            component = component.employeePicker,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = note.value,
            onValueChange = { note.value = it },
            label = { Text(text = "Note") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(
            onClick = { component.onUse(note = note.value) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Подтвердить использование")
        }
    }
}
