package ru.shafran.ui.error

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.error.Error
import ru.shafran.ui.R

@Composable
internal fun ErrorUI(component: Error, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(painterResource(id = component.icon),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp),
            tint = Color.Red)
        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(id = component.message))
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = component::onContinue, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.error_continue_button))
        }
    }
}