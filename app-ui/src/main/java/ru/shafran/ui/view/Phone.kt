package ru.shafran.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
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
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.logo_phone),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            number?.number?.formatAsPhoneNumber()
                ?: stringResource(R.string.customer_unknown_phone),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = style,
        )
    }
}


private fun String.filterDigits(): String {
    return filter(Char::isDigit).trim()
}

private fun String.formatAsRussianNumber(): String {
    var phone = filterDigits()
    if (phone.firstOrNull() != '7') phone = "7$phone"
    return phone.sliceIgnoreLast(0, 11)
}

private fun String.toPhoneNumber(): PhoneNumber {
    return PhoneNumber(this)
}

//TODO: bug
@Composable
fun PhoneInput(
    number: PhoneNumber,
    onNumberChange: (PhoneNumber) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = number.toString().formatAsRussianNumber(),
        onValueChange = {
            onNumberChange(it.formatAsRussianNumber().toPhoneNumber())
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = { Text(stringResource(R.string.input_phonenumber)) },
        visualTransformation = PhoneNumberVisualTransformation(),
        modifier = modifier,
    )
}


val PhoneNumber.Companion.Saver
    get() = listSaver<PhoneNumber, String>(
        save = { listOf<String>(it.number) },
        restore = { PhoneNumber(it.getOrElse(0) { "" }) }
    )

private fun String.sliceIgnoreLast(start: Int, end: Int = length): String {
    return if (end > length) {
        substring(start, length)
    } else {
        substring(start, end)
    }
}


class PhoneNumberVisualTransformation(
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(text.formatAsPhoneNumber(), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (text.isBlank()) return 0
                return when {
                    offset <= 1 -> {
                        2;
                    }
                    offset <= 4 -> {
                        offset + 3
                    }
                    offset <= 7 -> {
                        offset + 5;
                    }
                    offset <= 9 -> {
                        offset + 6;
                    }
                    else -> {
                        offset + 7;
                    }
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (text.isBlank()) return 0
                return when {
                    offset <= 2 -> {
                        1;
                    }
                    offset <= 7 -> {
                        offset - 3
                    }
                    offset <= 12 -> {
                        offset - 5;
                    }
                    offset <= 15 -> {
                        offset - 6;
                    }
                    else -> {
                        offset - 7;
                    }
                }
            }
        })
    }
}


private fun AnnotatedString.formatAsPhoneNumber(): AnnotatedString {
    val input = this.text.filterDigits()
    if (input.isBlank())
        return AnnotatedString("")
    return buildAnnotatedString {
        val code = input.first()
        if (code == '7') {
            append("+7")
            if (input.length > 1) {
                append(" (${input.sliceIgnoreLast(1, 4)}")
            }
            if (input.length >= 5) {
                append(") ${input.sliceIgnoreLast(4, 7)}")
            }
            if (input.length >= 8) {
                append(" ${input.sliceIgnoreLast(7, 9)}")
            }
            if (input.length >= 10) {
                append("-${input.sliceIgnoreLast(9, 11)}")
            }
        } else {
            append("+${input.first()}${input.sliceIgnoreLast(1, 16)}")
        }
    }
}

private fun String.formatAsPhoneNumber(): String {
    val input = filterDigits()
    if (input.isBlank())
        return ""
    return buildString {
        val code = input.first()
        if (code == '7') {
            append("+7")
            if (input.length > 1) {
                append(" (${input.sliceIgnoreLast(1, 4)}")
            }
            if (input.length >= 5) {
                append(") ${input.sliceIgnoreLast(4, 7)}")
            }
            if (input.length >= 8) {
                append(" ${input.sliceIgnoreLast(7, 9)}")
            }
            if (input.length >= 10) {
                append("-${input.sliceIgnoreLast(9, 11)}")
            }
        } else {
            append("+${input.first()}${input.sliceIgnoreLast(1, 16)}")
        }
    }
}

