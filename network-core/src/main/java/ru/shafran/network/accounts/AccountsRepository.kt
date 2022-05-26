package ru.shafran.network.accounts

import ru.shafran.network.account.data.GetAttachedAccountRequest
import ru.shafran.network.account.data.GetAttachedAccountResponse

interface AccountsRepository {

    suspend fun getAccount(request: GetAttachedAccountRequest): GetAttachedAccountResponse

}