package ru.shafran.network.session

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

interface SessionsRepository {
    suspend fun createSessionsForCustomer(request: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse
    suspend fun useSession(request: UseSessionRequest): UseSessionResponse
    suspend fun getSessionUsagesHistory(request: GetSessionUsagesHistoryRequest): GetSessionUsagesHistoryResponse
    suspend fun deactivateSession(request: DeactivateSessionRequest): DeactivateSessionResponse
    suspend fun getAllSessionsForCustomer(request: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse
    suspend fun getSessionsIgnoreDeactivatedForCustomer(request: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse
    suspend fun getSessionsStats(request: GetSessionsStatsRequest): GetSessionsStatsResponse
}