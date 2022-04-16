package ru.shafran.common.sessions.history

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.SessionUsageHistoryItem

class SessionsHistoryListComponent(
    override val history: List<SessionUsageHistoryItem>,
    override val onUpdate: (() -> Unit),
    override val onDetails: (Customer.ActivatedCustomer) -> Unit,
    override val onStats: () -> Unit,
) : SessionsHistoryList