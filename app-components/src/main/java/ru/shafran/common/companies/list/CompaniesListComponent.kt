package ru.shafran.common.companies.list

import ru.shafran.network.companies.data.Company

class CompaniesListComponent(
    override val companies: List<Company>,
    override val onSelect: (Company) -> Unit,
) : CompaniesList