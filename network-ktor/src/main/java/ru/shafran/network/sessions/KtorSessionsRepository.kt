package ru.shafran.network.sessions

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.CreateSessionForCustomerResponse
import ru.shafran.network.session.data.GetSessionUsagesHistoryRequest
import ru.shafran.network.session.data.GetSessionUsagesHistoryResponse
import ru.shafran.network.session.data.GetSessionsForCustomerRequest
import ru.shafran.network.session.data.GetSessionsForCustomerResponse
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.session.data.UseSessionResponse
import ru.shafran.network.tryRequest

internal class KtorSessionsRepository(
    private val httpClient: HttpClient,
) : SessionsRepository {

    override suspend fun getSessionUsagesHistory(data: GetSessionUsagesHistoryRequest): GetSessionUsagesHistoryResponse {
        return tryRequest {
            httpClient.get("sessions/getSessionUsagesHistory") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun getSessionsForCustomer(data: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse {
        return tryRequest {
            httpClient.get("sessions/getServiceSessionsForCustomer") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun createSessionsForCustomer(data: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse {
        return tryRequest {
            httpClient.post("sessions/createServiceSessionsForCustomer") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun useSession(data: UseSessionRequest): UseSessionResponse {
        return tryRequest {
            httpClient.put("sessions/useServiceSession") {
                setBody(data)
            }.body()
        }
    }

}