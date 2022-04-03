package ru.shafran.common.sessions

import ru.shafran.common.sessions.history.SessionsHistoryList
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.SessionUsageHistoryItem

class SessionsHistoryListComponent(
    override val history: List<SessionUsageHistoryItem>,
    override val onUpdate: (() -> Unit),
    override val onDetails: (Customer.ActivatedCustomer) -> Unit,
) : SessionsHistoryList