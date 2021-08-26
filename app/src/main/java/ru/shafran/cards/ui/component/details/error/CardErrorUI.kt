package ru.shafran.cards.ui.component.details.error

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R

@Composable
fun CardErrorUI(component: CardError, modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(painterResource(id = R.drawable.card_loading),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp),
        tint = Color.Red)
        Spacer(modifier = Modifier.height(10.dp))
        Text(component.message.collectAsState().value)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = component::onReview, modifier = Modifier.fillMaxWidth()) {
            Text("Продолжить")
        }
    }
}