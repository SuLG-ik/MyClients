package ru.shafran.cards.ui.component.employees.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface


@Composable
fun EmployeesListUI(
    component: EmployeesList,
    modifier: Modifier,
) {
    val employees = component.employees.collectAsState(null).value
    val refreshState = rememberSwipeRefreshState(employees == null)
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateEmployee) {
                Icon(rememberVectorPainter(image = Icons.Outlined.Add), contentDescription = "Add")
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when {
                employees == null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.25f))
                        Text("Загрузка...", style = MaterialTheme.typography.h5)
                    }
                }
                employees.isEmpty() -> {
                    Text("Добавьте работника", style = MaterialTheme.typography.h5)
                }
                else -> {
                    SwipeRefresh(state = refreshState, onRefresh = component::onUpdate) {
                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)) {
                            itemsIndexed(employees) { index, employee ->
                                EmployeeItem(
                                    employee = employee, modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { component.onSelected(employee.id) }
                                )
                                if (index <= employees.size) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}


@Composable
private fun EmployeeItem(employee: EmployeeModel, modifier: Modifier) {
    OutlinedSurface(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)) {
            Icon(painterResource(id = R.drawable.logo_employees), contentDescription = null)
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = employee.data.name, style = MaterialTheme.typography.h4)
        }
    }

}