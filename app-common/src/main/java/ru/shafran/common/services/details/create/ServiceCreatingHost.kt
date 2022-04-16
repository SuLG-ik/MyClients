package ru.shafran.common.services.details.create

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.CreateServiceRequest

interface ServiceCreatingHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class CreateService(val request: CreateServiceRequest?) : Configuration()

        @Parcelize
        data class CreateServiceLoading(val request: CreateServiceRequest) : Configuration()

        @Parcelize
        data class UnknownError(
            val request: CreateServiceRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class CreateService(
            val component: ServiceCreating,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()



    }

}