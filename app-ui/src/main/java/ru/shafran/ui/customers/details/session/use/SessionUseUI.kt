package ru.shafran.ui.customers.details.session.use

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import ru.shafran.common.customers.details.sessions.use.SessionUse
import ru.shafran.common.loading.Loading
import ru.shafran.ui.R
import ru.shafran.ui.employees.picker.FloatingEmployeePickerUI
import ru.shafran.ui.view.PlaceholderTextField
import ru.shafran.ui.view.TitledDialog

@Composable
fun SessionUsePlaceholderUI(
    component: Loading,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = {
            Text(
                stringResource(
                    id = component.message,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = { },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PlaceholderTextField(modifier = Modifier.fillMaxWidth())
            PlaceholderTextField(modifier = Modifier.fillMaxWidth())
            OutlinedButton(
                onClick = { },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Подтвердить использование", modifier = Modifier
                    .placeholder(true, highlight = PlaceholderHighlight.fade()))
            }
        }

    }
}

@Composable
fun SessionUseUI(
    component: SessionUse,
    modifier: Modifier = Modifier,
) {
    TitledDialog(
        title = {
            Text(
                stringResource(
                    id = R.string.customer_session_activation_title,
                    component.session.activation.service.info.title,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onBackPressed = component::onBack,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
}
