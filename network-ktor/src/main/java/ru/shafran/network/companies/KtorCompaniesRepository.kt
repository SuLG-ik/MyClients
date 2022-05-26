package ru.shafran.network.companies

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.companies.data.GetAvailableCompaniesListRequest
import ru.shafran.network.companies.data.GetAvailableCompaniesListResponse
import ru.shafran.network.tryRequest

class KtorCompaniesRepository(
    private val client: HttpClient,
) : CompaniesRepository {
    override suspend fun getAvailableCompaniesList(request: GetAvailableCompaniesListRequest): GetAvailableCompaniesListResponse {
        return tryRequest {
            client.get("companies/getAvailableCompaniesList") { setBody(request) }
        }
    }
}