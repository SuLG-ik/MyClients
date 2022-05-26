package ru.shafran.ui.companies

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.common.companies.CompanySelector
import ru.shafran.ui.auth.AppLogo
import ru.shafran.ui.companies.list.CompaniesListUI
import ru.shafran.ui.loading.LoadingUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompaniesSelectorUI(
    component: CompanySelector,
    modifier: Modifier,
) {
    Surface {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                AppLogo(
                    isLoading = component.routerState.subscribeAsState().value.activeChild.configuration is CompanySelector.Configuration.Loading,
                    modifier = Modifier.size(200.dp)
                )
                Children(component.routerState) {
                    CompaniesSelectorNavHost(
                        child = it.instance,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CompaniesSelectorNavHost(
    child: CompanySelector.Child,
    modifier: Modifier,
) {
    when (child) {
        is CompanySelector.Child.CompaniesList ->
            CompaniesListUI(component = child.component, modifier = modifier)
        is CompanySelector.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
    }
}