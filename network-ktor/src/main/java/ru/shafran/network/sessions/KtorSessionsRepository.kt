package ru.shafran.network.sessions

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.session.data.*

internal class KtorSessionsRepository(
    private val httpClient: HttpClient,
) : SessionsRepository {

    override suspend fun getSessionsForCustomer(data: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse {
        return httpClient.get(
            path = "/sessions/getServiceSessionsForCustomer",
            body = data,
        )
    }

    override suspend fun createSessionsForCustomer(data: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse {
        return httpClient.post(
            path = "/sessions/createServiceSessionsForCustomer",
            body = data,
        )
    }

    override suspend fun useSession(data: UseSessionRequest): UseSessionResponse {
        return httpClient.put(
            path = "/sessions/useServiceSession",
            body = data,
        )
    }

}