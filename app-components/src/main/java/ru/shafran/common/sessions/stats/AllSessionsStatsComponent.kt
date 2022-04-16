package ru.shafran.common.sessions.stats

import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod

class AllSessionsStatsComponent(
    override val period: DatePeriod,
    override val stats: SessionStats,
    override val onChangePeriod: (DatePeriod) -> Unit,
) : AllSessionsStats