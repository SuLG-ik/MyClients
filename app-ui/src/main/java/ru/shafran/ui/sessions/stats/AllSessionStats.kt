package ru.shafran.ui.sessions.stats

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.shafran.common.sessions.stats.AllSessionsStats
import ru.shafran.network.services.data.ServiceConfiguration
import ru.shafran.network.session.data.MostPopularService
import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod
import ru.shafran.ui.R
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.TitledDialog
import java.time.LocalDate

@Composable
fun AllSessionStatsUI(
    component: AllSessionsStats,
    modifier: Modifier,
) {
    TitledDialog(
        title = {
            Text(stringResource(id = R.string.sessions_stats_title))
        },
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        PeriodSelector(
            currentPeriod = component.period,
            onChangePeriod = component.onChangePeriod,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        )
        SessionsStats(
            stats = component.stats,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private val oneMonth get() = DatePeriod(LocalDate.now().minusMonths(1), LocalDate.now())
private val threeMonth get() = DatePeriod(LocalDate.now().minusMonths(3), LocalDate.now())
private val oneYear get() = DatePeriod(LocalDate.now().minusYears(1), LocalDate.now())

@Composable
fun PeriodSelector(
    currentPeriod: DatePeriod,
    onChangePeriod: (DatePeriod) -> Unit,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier,
    ) {
        MonthPeriod(
            isSelected = currentPeriod == oneMonth,
            onChangePeriod = onChangePeriod,
        )
        ThreeMonthPeriod(
            isSelected = currentPeriod == threeMonth,
            onChangePeriod = onChangePeriod,
        )
        YearPeriod(
            isSelected = currentPeriod == oneYear,
            onChangePeriod = onChangePeriod,
        )
    }
}

@Composable
fun ThreeMonthPeriod(
    isSelected: Boolean,
    onChangePeriod: (DatePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedSurface(
        onClick = { onChangePeriod(threeMonth) },
        modifier = modifier,
        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    ) {
        Text(
            text = "Три месяца",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun YearPeriod(
    isSelected: Boolean,
    onChangePeriod: (DatePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedSurface(
        onClick = { onChangePeriod(oneYear) },
        modifier = modifier,
        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    ) {
        Text(
            text = "Год",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(5.dp),
        )
    }
}


@Composable
fun MonthPeriod(
    isSelected: Boolean,
    onChangePeriod: (DatePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedSurface(
        onClick = { onChangePeriod(oneMonth) },
        modifier = modifier,
        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    ) {
        Text(
            text = "Месяц",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun SessionsStats(
    stats: SessionStats,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("Активированных услуг: ${stats.activatedSessionsCount}")
                Text("Использованных услуг: ${stats.usedSessionsCount}")
            }
        }
        OutlinedSurface(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("Самая популярная услуга", style = MaterialTheme.typography.titleMedium)
                ServiceStats(
                    stats.popularService, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ServiceStats(stats: MostPopularService, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        Icon(
            painterResource(id = R.drawable.service_logo),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            androidx.compose.material3.Text(
                stats.configuredService.data.info.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                stats.configuredService.data.configurations.forEach {
                    Configuration(configuration = it)
                }
            }
        }
    }
}

@Composable
private fun Configuration(configuration: ServiceConfiguration, modifier: Modifier = Modifier) {
    OutlinedSurface(modifier) {
        androidx.compose.material3.Text(
            text = configuration.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(5.dp),
        )
    }
}