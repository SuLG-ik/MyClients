package ru.shafran.network.auth

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.account.data.Account
import ru.shafran.network.auth.data.LoginAccountRequest

interface AuthLoginStore :
    Store<AuthLoginStore.Intent, AuthLoginStore.State, AuthLoginStore.Label> {

    sealed class Intent {

        class PrefetchLogin : Intent()

        data class Login(
            val request: LoginAccountRequest,
        ) : Intent()

    }

    sealed class State {

        object Empty : State()

        class AccountLoginRequest : State()

        data class AccountLoginLoading(
            val request: LoginAccountRequest,
        ) : State()

        data class AccountLoginCompleted(
            val account: Account,
        ) : State()

        sealed class Error : State() {
            object Unknown : Error()
            object IllegalCredentials : Error()
        }

    }

    sealed class Label

}