package ru.shafran.ui.companies.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.shafran.common.companies.list.CompaniesList
import ru.shafran.network.companies.data.Company
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.TitledDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompaniesListUI(
    component: CompaniesList,
    modifier: Modifier,
) {
    TitledDialog(
        title = { Text("Компании") },
        modifier = modifier,
        contentPadding = PaddingValues(0.dp)
    ) {
        OutlinedSurface(

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(component.companies) {
                        CompanyItem(
                            company = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement()
                                .clickable { component.onSelect(it) }
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun CompanyItem(
    company: Company,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                painterResource(id = R.drawable.company),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Column {
                Text(
                    text = company.data.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = company.data.codeName,
                    style = MaterialTheme.typography.bodySmall
                        .copy(color = MaterialTheme.typography.bodySmall.color.copy(alpha = 0.7f))
                )
            }
        }
    }
}
