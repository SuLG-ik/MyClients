package ru.shafran.cards.ui.component.employeesdetails

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.ui.component.createemployee.CreateEmployeeUI
import ru.shafran.cards.ui.component.employeeinfo.EmployeeInfoUI
import ru.shafran.cards.ui.component.loading.LoadingUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmployeesDetailsUI(
    component: EmployeesDetails,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) {
        if (it == ModalBottomSheetValue.Hidden) {
            component.onHide()
        }
        true
    }
    val isShown by component.isShown.subscribeAsState()

    LaunchedEffect(key1 = isShown, block = {
        if (isShown)
            state.show()
        else {
            state.hide()
        }
    })
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Children(routerState = component.routerState.subscribeAsState().value) {
                    EmployeesDetailsRouter(
                        instance = it.instance,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )
                }
            }
        },
        sheetElevation = 0.dp,
        sheetShape = MaterialTheme.shapes.large.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
        modifier = modifier,
        content = content,
    )

}

@Composable
private fun EmployeesDetailsRouter(instance: EmployeesDetails.Child, modifier: Modifier) {
    when (instance) {
        is EmployeesDetails.Child.Loading -> {
            LoadingUI(
                component = instance.component,
                modifier = modifier
            )
        }
        is EmployeesDetails.Child.CreateEmployee -> CreateEmployeeUI(
            component = instance.component,
            modifier = modifier
        )
        is EmployeesDetails.Child.EmployeeInfo -> EmployeeInfoUI(
            component = instance.component,
            modifier = modifier
        )
        EmployeesDetails.Child.Hidden -> Text("")
    }
}