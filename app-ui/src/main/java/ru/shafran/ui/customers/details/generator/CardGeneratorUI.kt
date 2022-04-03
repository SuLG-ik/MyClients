package ru.shafran.ui.customers.details.generator

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import ru.shafran.common.customers.details.generator.CardGenerator
import ru.shafran.ui.R
import ru.shafran.ui.customers.details.edit.CustomerEditorUI
import ru.shafran.ui.view.OutlinedTitledDialog

@Composable
fun CustomerGeneratorUI(component: CardGenerator, modifier: Modifier) {
    OutlinedTitledDialog(
        title = {
            Text(
                stringResource(R.string.customer_generate_request_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        modifier = modifier,
    ) {
        CustomerEditorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
