package ru.shafran.ui.services.details.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.services.details.edit.ServiceEditing
import ru.shafran.ui.view.TitledDialog

@Composable
fun ServiceEditingUI(
    component: ServiceEditing,
    modifier: Modifier,
) {
    TitledDialog(
        title = { Text("Редактирование услуги") },
        onBackPressed = component.onBack,
        modifier = modifier,
    ) {
        ServiceEditorUI(
            component = component.editor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}