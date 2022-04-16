package ru.shafran.ui.employees.details.host

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.common.employees.details.host.EmployeeDetailsHost
import ru.shafran.ui.employees.details.create.EmployeeCreatingHostUI
import ru.shafran.ui.employees.details.info.EmployeeInfoHostUI
import ru.shafran.ui.view.BottomSheetComponentUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmployeeDetailsHostUI(
    component: EmployeeDetailsHost,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit),
) {
    BottomSheetComponentUI(
        router = component.routerState,
        isHidden = { it is EmployeeDetailsHost.Child.Hidden },
        onHide = component.onHide,
        navHost = {
            EmployeeNavHost(
                child = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )
        },
        modifier = modifier,
        content = content,
    )
}


@Composable
private fun EmployeeNavHost(
    child: EmployeeDetailsHost.Child,
    modifier: Modifier,
) {
    when (child) {
        is EmployeeDetailsHost.Child.EmployeeInfo ->
            EmployeeInfoHostUI(component = child.component, modifier = modifier)
        is EmployeeDetailsHost.Child.Hidden -> Text("")
        is EmployeeDetailsHost.Child.EmployeeCreating ->
            EmployeeCreatingHostUI(component = child.component, modifier = modifier)
    }
}