package ru.shafran.common.employees.details.create

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeRequestData

interface EmployeeCreatingHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class CreateEmployee(val request: CreateEmployeeRequestData? = null) : Configuration()

        @Parcelize
        data class CreateEmployeeLoading(val request: CreateEmployeeRequest) :
            Configuration()

        @Parcelize
        data class UnknownError(
            val request: CreateEmployeeRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class CreateEmployee(
            val component: EmployeeCreating,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }
}