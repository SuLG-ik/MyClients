package ru.shafran.network.session

import ru.shafran.network.session.data.*

interface SessionsRepository {
    suspend fun getSessionsForCustomer(data: GetSessionsForCustomerRequest): GetSessionsForCustomerResponse
    suspend fun createSessionsForCustomer(data: CreateSessionForCustomerRequest): CreateSessionForCustomerResponse
    suspend fun useSession(data: UseSessionRequest): UseSessionResponse
}