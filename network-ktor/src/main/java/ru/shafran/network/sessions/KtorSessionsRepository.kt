package ru.shafran.network.sessions

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.CreateSessionForCustomerResponse
import ru.shafran.network.session.data.DeactivateSessionRequest
import ru.shafran.network.session.data.DeactivateSessionResponse
import ru.shafran.network.session.data.GetSessionUsagesHistoryRequest
import ru.shafran.network.session.data.GetSessionUsagesHistoryResponse
import ru.shafran.network.session.data.GetSessionsForCustomerRequest
import ru.shafran.network.session.data.GetSessionsForCustomerResponse
import ru.shafran.network.session.data.GetSessionsStatsRequest
import ru.shafran.network.session.data.GetSessionsStatsResponse
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.session.data.UseSessionResponse
import ru.shafran.network.tryRequest

internal class KtorSessionsRepository(
    private val httpClient: HttpClient,
) : SessionsRepository {


    override suspend fun getSessionsStats(request: GetSessionsStatsRequest): GetSessionsStatsResponse {
        return tryRequest {
            httpClient.get("sessions/getSessionsStats") {
                setBody(request)
            }
        }
    }

    override suspend fun getSessionUsagesHistory(request: GetSessionUsagesHistoryRequest): GetSessionUsagesHistoryResponse {
        return tryRequest {
            httpClient.get("sessions/getSessionUsagesHistory") {
                setBody(request)
            }
        }
    }

    override suspend fun getSessionsIgnoreDeactivatedForCustomer(request: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse {
        return tryRequest {
            httpClient.get("sessions/getServiceSessionsIgnoreDeactivatedForCustomer") {
                setBody(request)
            }
        }
    }

    override suspend fun deactivateSession(request: DeactivateSessionRequest): DeactivateSessionResponse {
        return tryRequest {
            httpClient.delete("sessions/deactivateServiceSession") {
                setBody(request)
            }
        }
    }

    override suspend fun getAllSessionsForCustomer(request: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse {
        return tryRequest {
            httpClient.get("sessions/getAllServiceSessionsForCustomer") {
                setBody(request)
            }
        }
    }

    override suspend fun createSessionsForCustomer(request: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse {
        return tryRequest {
            httpClient.post("sessions/createServiceSessionsForCustomer") {
                setBody(request)
            }
        }
    }

    override suspend fun useSession(request: UseSessionRequest): UseSessionResponse {
        return tryRequest {
            httpClient.put("sessions/useServiceSession") {
                setBody(request)
            }
        }
    }

}