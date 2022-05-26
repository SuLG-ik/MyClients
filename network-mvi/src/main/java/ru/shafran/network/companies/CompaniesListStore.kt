package ru.shafran.network.companies

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.Company

interface CompaniesListStore :
    Store<CompaniesListStore.Intent, CompaniesListStore.State, CompaniesListStore.Label> {

    sealed class Intent {
        class LoadCompaniesList(
            val accountId: String,
            val offset: Int = 30,
            val page: Int = 0,
        ) : Intent()
    }

    sealed class State {

        object Empty : State()

        object CompaniesListLoading : State()

        class CompaniesList(
            val companies: List<Company>,
        ) : State()

        sealed class Error : State() {
            class Unknown : Error()
        }

    }

    sealed class Label

}