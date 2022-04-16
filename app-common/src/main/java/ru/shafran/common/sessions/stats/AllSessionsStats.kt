package ru.shafran.common.sessions.stats

import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod

interface AllSessionsStats {

    val period: DatePeriod
    val stats: SessionStats

    val onChangePeriod: (DatePeriod) -> Unit

}