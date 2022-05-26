package ru.shafran.common.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.auth.AccountAuth
import ru.shafran.common.root.CompanyTargetApplication
import ru.shafran.network.account.data.Account
import ru.shafran.network.companies.data.Company

interface Main {

    val isLoading: Value<Boolean>

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Splash : Configuration()

        @Parcelize
        object Error : Configuration()

        @Parcelize
        object Auth : Configuration()

        @Parcelize
        data class CompanySelector(
            val account: Account,
        ) : Configuration()


        @Parcelize
        data class CompanyTargetApplication(
            val company: Company,
        ) : Configuration()

    }


    sealed class Child {

        class Auth(
            val component: AccountAuth,
        ) : Child()

        class Application(
            val component: CompanyTargetApplication,
        ) : Child()

        class CompanySelector(
            val component: ru.shafran.common.companies.CompanySelector,
        ) : Child()


        class Splash(
            val component: ru.shafran.common.main.Splash,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }

}