package ru.shafran.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.shafran.network.PhoneNumber
import ru.shafran.ui.R

@Composable
internal fun Phone(
    number: PhoneNumber?,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.logo_phone),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            number?.toString() ?: stringResource(R.string.customer_unknown_phone),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = style,
        )
    }
}

@Preview
@Composable
private fun PhonePreview() {
    Phone(
        number = PhoneNumber("7", "9005553535")
    )
}