package ru.shafran.common.auth

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.network.account.data.Account

interface AccountAuth {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object AuthType : Configuration()

        @Parcelize
        object LoginRequest : Configuration()

        @Parcelize
        data class LoadingCompleted(
            val account: Account,
        ) : Configuration()

        @Parcelize
        object IllegalCredentials : Configuration()

        @Parcelize
        object UnknownError : Configuration()

    }

    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class AuthType(
            val component: ru.shafran.common.auth.type.AuthType,
        ) : Child()

        class LoginRequest(
            val component: ru.shafran.common.auth.login.AuthLogin,
        ) : Child()

        class LoadingCompleted(
            val component: ru.shafran.common.auth.completed.AuthCompleted,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()

    }

    val isLoading: StateFlow<Boolean>
}