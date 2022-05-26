package ru.shafran.common.auth.completed

import ru.shafran.network.account.data.Account

class AuthCompletedComponent(
    override val account: Account,
    override val onContinue: (Account) -> Unit,
) : AuthCompleted