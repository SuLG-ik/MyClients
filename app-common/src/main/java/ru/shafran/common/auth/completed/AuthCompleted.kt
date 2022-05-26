package ru.shafran.common.auth.completed

import ru.shafran.network.account.data.Account

interface AuthCompleted {

    val account: Account

    val onContinue: (Account) -> Unit

}