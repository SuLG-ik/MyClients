package ru.shafran.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.shafran.common.auth.login.AuthLogin
import ru.shafran.network.auth.data.LoginAccountRequest
import ru.shafran.ui.R

@Composable
fun AuthLoginUI(
    component: AuthLogin,
    modifier: Modifier,
) {
    LoginRequest(
        onLogin = component.onLogin,
        modifier = modifier
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginRequest(
    onLogin: (LoginAccountRequest.UsernameAndPassword) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        val isPasswordHidden = rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = username.value,
            onValueChange = {
                username.value = it.trim()
            },
            label = { Text(stringResource(R.string.auth_login_username)) },
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
            value = password.value,
            onValueChange = {
                password.value = it.trim()
            },
            label = { Text(stringResource(R.string.auth_login_password)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) },
                onDone = {
                    focus.clearFocus()
                    keyboard?.hide()
                }
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordHidden.value = !isPasswordHidden.value }) {
                    if (isPasswordHidden.value) {
                        Icon(
                            painter = painterResource(id = R.drawable.password_hidden),
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.password_shown),
                            contentDescription = null,
                        )
                    }
                }
            },
            visualTransformation = if (isPasswordHidden.value) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false)
        )
        FilledTonalButton(
            onClick = {
                onLogin(
                    LoginAccountRequest.UsernameAndPassword(
                        username = username.value, password = password.value
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
            enabled = username.value.length >= 6 && password.value.length >= 6
        ) {
            Text("Войти")
        }
    }
}