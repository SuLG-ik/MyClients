package ru.shafran.network.sessions

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.CreateSessionForCustomerResponse
import ru.shafran.network.session.data.GetSessionsForCustomerRequest
import ru.shafran.network.session.data.GetSessionsForCustomerResponse
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.session.data.UseSessionResponse

internal class KtorSessionsRepository(
    private val httpClient: HttpClient,
) : SessionsRepository {

    override suspend fun getSessionsForCustomer(data: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse {
        return httpClient.get("sessions/getServiceSessionsForCustomer") {
            setBody(data)
        }.body()
    }

    override suspend fun createSessionsForCustomer(data: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse {
        return httpClient.post("sessions/createServiceSessionsForCustomer") {
            setBody(data)
        }.body()
    }

    override suspend fun useSession(data: UseSessionRequest): UseSessionResponse {
        return httpClient.put("sessions/useServiceSession") {
            setBody(data)
        }.body()
    }

}