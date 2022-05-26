package ru.shafran.ui.auth.type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.auth.type.AuthType

@Composable
fun AuthTypeUI(
    component: AuthType,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Регистрация")
        }
        FilledTonalButton(
            onClick = component.onLoginUsernameAndPasswordLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }
    }
}