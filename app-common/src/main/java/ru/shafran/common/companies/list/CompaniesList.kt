package ru.shafran.common.companies.list

import ru.shafran.network.companies.data.Company

interface CompaniesList {

    val companies: List<Company>

    val onSelect: (Company) -> Unit

}