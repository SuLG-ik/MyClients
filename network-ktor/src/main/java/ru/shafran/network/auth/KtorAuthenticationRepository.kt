package ru.shafran.network.auth

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.auth.data.LoginAccountRequest
import ru.shafran.network.auth.data.LoginAccountResponse
import ru.shafran.network.tryRequest

class KtorAuthenticationRepository(
    private val client: HttpClient,
) : AuthenticationRepository {
    override suspend fun login(request: LoginAccountRequest): LoginAccountResponse {
        return tryRequest {
            client.get("auth/login") { setBody(request) }
        }
    }
}