package ru.shafran.network.session

import ru.shafran.network.session.data.CreateSessionForCustomerRequest
import ru.shafran.network.session.data.CreateSessionForCustomerResponse
import ru.shafran.network.session.data.GetSessionUsagesHistoryRequest
import ru.shafran.network.session.data.GetSessionUsagesHistoryResponse
import ru.shafran.network.session.data.GetSessionsForCustomerRequest
import ru.shafran.network.session.data.GetSessionsForCustomerResponse
import ru.shafran.network.session.data.UseSessionRequest
import ru.shafran.network.session.data.UseSessionResponse

interface SessionsRepository {
    suspend fun getSessionsForCustomer(data: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse
    suspend fun createSessionsForCustomer(data: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse
    suspend fun useSession(data: UseSessionRequest): UseSessionResponse
    suspend fun getSessionUsagesHistory(data: GetSessionUsagesHistoryRequest): GetSessionUsagesHistoryResponse
}