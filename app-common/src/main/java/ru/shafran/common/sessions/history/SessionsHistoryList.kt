package ru.shafran.common.sessions.history

import ru.shafran.common.utils.Updatable
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.SessionUsageHistoryItem


interface SessionsHistoryList : Updatable {

    val history: List<SessionUsageHistoryItem>

    override val onUpdate: (() -> Unit)

    val onDetails: (Customer.ActivatedCustomer) -> Unit

    val onStats: () -> Unit

}