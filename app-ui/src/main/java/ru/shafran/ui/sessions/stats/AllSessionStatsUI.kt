package ru.shafran.ui.sessions.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.shafran.common.sessions.stats.AllSessionsStatsHost
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@Composable
fun AllSessionStatsHostUI(
    component: AllSessionsStatsHost,
    modifier: Modifier,
) {
    Children(routerState = component.routerState) {
        AllSessionsStatsNavHost(child = it.instance, modifier = modifier)
    }
}

@Composable
fun AllSessionsStatsNavHost(child: AllSessionsStatsHost.Child, modifier: Modifier) {
    when (child) {
        is AllSessionsStatsHost.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
        is AllSessionsStatsHost.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is AllSessionsStatsHost.Child.StatsLoaded ->
            AllSessionStatsUI(component = child.component, modifier = modifier)
    }
}