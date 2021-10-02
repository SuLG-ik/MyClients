package ru.shafran.cards.ui.component.employees.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.cards.R
import ru.shafran.cards.data.employee.EmployeeModel

@Composable
fun EmployeeInfoUI(component: EmployeeInfo, modifier: Modifier) {
    EmployeeInfo(
        employee = component.employee,
        onEdit = component::onEdit,
        onDelete = component::onDelete,
        modifier = modifier,
    )
}

@Composable
private fun EmployeeInfo(
    employee: EmployeeModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.logo_employees),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = employee.data.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Row {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onEdit),
            ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Outlined.Edit),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
            )
        }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onDelete),
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Close),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                    .padding(5.dp),
                )
            }
        }
    }
}
