package ru.shafran.network.companies

import ru.shafran.network.companies.data.GetAvailableCompaniesListRequest
import ru.shafran.network.companies.data.GetAvailableCompaniesListResponse

interface CompaniesRepository {

    suspend fun getAvailableCompaniesList(request: GetAvailableCompaniesListRequest): GetAvailableCompaniesListResponse

}