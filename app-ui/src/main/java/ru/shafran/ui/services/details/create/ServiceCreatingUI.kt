package ru.shafran.ui.services.details.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.services.details.create.ServiceCreating
import ru.shafran.ui.services.details.edit.ServiceEditorUI
import ru.shafran.ui.view.TitledDialog

@Composable
fun ServiceCreatingUI(
    component: ServiceCreating,
    modifier: Modifier,
) {
    TitledDialog(
        title = { Text("Создание услуги") },
        modifier = modifier,
    ) {
        ServiceEditorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}