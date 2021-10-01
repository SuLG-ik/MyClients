package ru.shafran.cards.ui.component.employeeslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val employees by component.employees.collectAsState(null)
    val refreshState = rememberSwipeRefreshState(employees == null)
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateEmployee) {

            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (employees?.isNotEmpty() == true) {
                employees?.let {
                    SwipeRefresh(state = refreshState, onRefresh = component::onUpdate) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            itemsIndexed(it) { index, employee ->
                                EmployeeItem(
                                    employee = employee, modifier = Modifier
                                        .fillMaxWidth()
                                )
                                if (index <= it.size) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                    }
                } ?: CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.25f))
            } else {
                Text("Пусто")
                OutlinedButton(onClick = { }) {
                    Text("Отсканировать")
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