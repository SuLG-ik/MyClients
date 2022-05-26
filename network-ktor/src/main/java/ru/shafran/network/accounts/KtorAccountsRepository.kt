package ru.shafran.network.accounts

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.account.data.GetAttachedAccountRequest
import ru.shafran.network.account.data.GetAttachedAccountResponse
import ru.shafran.network.tryRequest

class KtorAccountsRepository(
    private val client: HttpClient,
) : AccountsRepository {
    override suspend fun getAccount(request: GetAttachedAccountRequest): GetAttachedAccountResponse {
        return tryRequest {
            client.get("accounts/getAccount") { setBody(request) }
        }
    }
}