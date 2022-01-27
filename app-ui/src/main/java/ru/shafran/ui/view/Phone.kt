package ru.shafran.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.shafran.network.PhoneNumber
import ru.shafran.ui.R

@Composable
internal fun Phone(
    number: PhoneNumber?,
    modifier: Modifier = Modifier,
    fontStyle: FontStyle? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.logo_phone),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (number != null)
            Text(
                number.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontStyle = fontStyle,
            )
        else {
            Text(
                stringResource(R.string.customer_unknown_phone),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontStyle = fontStyle,
            )
        }
    }
}


@Preview
@Composable
private fun PhonePreview() {
    Phone(
        number = PhoneNumber("7", "9005553535")
    )
}