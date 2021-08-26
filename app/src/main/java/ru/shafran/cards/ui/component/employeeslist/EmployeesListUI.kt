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
import com.google.accompanist.placeholder.material.placeholder
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.Employee
import ru.shafran.cards.data.employee.EmployeeData


@Composable
fun EmployeesListUI(component: EmployeesList, modifier: Modifier) {
    val employees by component.employees.collectAsState()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateEmployee) {

            }
        }
    ) {
        employees.let {
            if (it == null) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(5) {
                        EmployeeItem(employee = fakeEmployee,
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(true))
                    }
                }
            } else {
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


}

private val fakeEmployee: Employee =
    Employee(
        id = -1,
        data = EmployeeData(
            name = "Unknown Name"
        )
    )


@Composable
private fun EmployeeItem(employee: Employee, modifier: Modifier) {
    Row(modifier) {
        Icon(painterResource(id = R.drawable.logo_employees), contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = employee.data.name, style = MaterialTheme.typography.h4)
    }
}