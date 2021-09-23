package ru.shafran.cards.ui.component.employeeslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.EmployeeModel


@Composable
fun EmployeesListUI(component: EmployeesList, modifier: Modifier) {
    val employees by component.employees.collectAsState(emptyList())

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateEmployee) {

            }
        }
    ) {
        employees.let {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(it) { index, employee ->
                    EmployeeItem(employee = employee, modifier = Modifier
                        .fillMaxWidth())
                    if (index <= it.size) {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }


}

private val fakeEmployee: EmployeeModel =
    EmployeeModel(
        id = -1,
        data = EmployeeDataModel(
            name = "Unknown Name"
        )
    )


@Composable
private fun EmployeeItem(employee: EmployeeModel, modifier: Modifier) {
    Row(modifier) {
        Icon(painterResource(id = R.drawable.logo_employees), contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = employee.data.name, style = MaterialTheme.typography.h4)
    }
}