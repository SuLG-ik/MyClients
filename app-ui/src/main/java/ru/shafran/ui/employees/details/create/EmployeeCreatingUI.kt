package ru.shafran.ui.employees.details.create

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.employees.details.create.EmployeeCreating
import ru.shafran.ui.R
import ru.shafran.ui.view.TitledDialog

@Composable
fun EmployeeCreatingUI(
    component: EmployeeCreating,
    modifier: Modifier,
) {
    TitledDialog(
        title = { Text(stringResource(id = R.string.employee_create_title)) },
        modifier = modifier,
        contentPadding = PaddingValues(0.dp)
    ) {
        EmployeeCreatorUI(
            component = component.creator,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}