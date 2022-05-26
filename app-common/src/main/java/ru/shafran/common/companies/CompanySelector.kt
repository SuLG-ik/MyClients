package ru.shafran.common.companies

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.account.data.Account
import ru.shafran.network.companies.data.Company

interface CompanySelector {

    val account: Account

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        class Loading : Configuration()

        @Parcelize
        class CompaniesList(val companies: List<Company>) : Configuration()

    }

    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class CompaniesList(
            val component: ru.shafran.common.companies.list.CompaniesList,
        ) : Child()

    }


}