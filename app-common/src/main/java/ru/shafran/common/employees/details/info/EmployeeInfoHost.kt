package ru.shafran.common.employees.details.info

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.employees.data.Employee

interface EmployeeInfoHost {

    val routerState: Value<RouterState<Configuration, Child>>

    val onBack: (() -> Unit)?


    sealed class Configuration : Parcelable {

        @Parcelize
        class Loading : Configuration()

        @Parcelize
        object UnknownError : Configuration()

        @Parcelize
        class EmployeeLoaded(val employee: Employee) : Configuration()

    }

    sealed class Child {

        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class Error(val component: ru.shafran.common.error.Error) : Child()

        class EmployeeLoaded(val component: EmployeeInfo) : Child()

    }

}