package ru.shafran.ui.auth.completed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.auth.completed.AuthCompleted
import ru.shafran.ui.R

@Composable
fun LoadingCompletedUI(
    component: AuthCompleted,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.single_employee),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Добро пожалавоть, ${component.account.data.name}",
            style = MaterialTheme.typography.titleLarge,
        )
        FilledTonalButton(
            onClick = { component.onContinue(component.account) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Продолжить")
        }
    }
}