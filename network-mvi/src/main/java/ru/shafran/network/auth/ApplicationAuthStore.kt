package ru.shafran.network.auth

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.account.data.Account
import ru.shafran.network.companies.data.Company

interface ApplicationAuthStore :
    Store<ApplicationAuthStore.Intent, ApplicationAuthStore.State, ApplicationAuthStore.Label> {

    sealed class Intent {

        object LoadAccountAndCompany : Intent()

        class LoadCompanySelector(
            val account: Account,
        ) : Intent()

        class LoadApplication(
            val account: Account,
            val company: Company,
        ) : Intent()

    }


    sealed class State {
        object Empty : State()

        class AuthAccount() : State()

        data class CompanySelector(
            val account: Account,
        ) : State()

        data class Application(
            val company: Company,
        ) : State()

        sealed class Error : State() {
            class Unknown : Error()
        }

    }

    sealed class Label

}