package ru.shafran.ui.root

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.RouterState
import ru.shafran.common.root.CompanyTargetApplication
import ru.shafran.common.utils.NavigationItem
import ru.shafran.ui.R
import ru.shafran.ui.customers.CustomersUI
import ru.shafran.ui.employees.EmployeesUI
import ru.shafran.ui.scanner.CustomerScannerUI
import ru.shafran.ui.services.ServicesUI
import ru.shafran.ui.view.FloatingBottomNavigation

private val CHILDREN: Map<CompanyTargetApplication.Configuration, NavigationItem> by lazy {
    mapOf(
        CompanyTargetApplication.Configuration.CustomerScanner to NavigationItem(
            R.string.navigation_scanner_title,
            R.drawable.logo_cards,
        ),
        CompanyTargetApplication.Configuration.Services to NavigationItem(
            R.string.navigation_services_title,
            R.drawable.logo_services,
        ),
        CompanyTargetApplication.Configuration.Customers to NavigationItem(
            R.string.navigation_customers_title,
            R.drawable.logo_customers,
        ),
        CompanyTargetApplication.Configuration.Employees to NavigationItem(
            R.string.navigation_employees_title,
            R.drawable.logo_employees,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RootUI(component: CompanyTargetApplication, modifier: Modifier = Modifier) {
    val currentChild = component.routerState.subscribeAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = component.company.data.title,
                    )
                }
            )
        },
        bottomBar = {
            FloatingBottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                CHILDREN.forEach {
                    RootNavigationItem(
                        currentConfiguration = currentChild.value.activeChild.configuration,
                        service = it.key,
                        item = it.value,
                        onClick = { component.onNavigate(it.key) },
                    )
                }
            }
        },
        modifier = modifier,
    ) {
        RootNavHost(
            value = currentChild.value,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        )
    }
}

@Composable
private fun RootNavHost(
    value: RouterState<CompanyTargetApplication.Configuration, CompanyTargetApplication.Child<Any?>>,
    modifier: Modifier,
) {
    Children(routerState = value) {
        when (val instance = it.instance) {
            is CompanyTargetApplication.Child.CustomerScanner ->
                CustomerScannerUI(component = instance.component, modifier = modifier)
            is CompanyTargetApplication.Child.Services ->
                ServicesUI(component = instance.component, modifier = modifier)
            is CompanyTargetApplication.Child.Customers ->
                CustomersUI(customers = instance.component, modifier = modifier)
            is CompanyTargetApplication.Child.Employees ->
                EmployeesUI(component = instance.component, modifier = modifier)
        }
    }
}


@Composable
private fun RowScope.RootNavigationItem(
    currentConfiguration: CompanyTargetApplication.Configuration,
    service: CompanyTargetApplication.Configuration,
    item: NavigationItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        selected = currentConfiguration == service,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = stringResource(id = item.label)
            )
        },
        modifier = modifier,
    )
}