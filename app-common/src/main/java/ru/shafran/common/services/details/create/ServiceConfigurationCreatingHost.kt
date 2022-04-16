package ru.shafran.common.services.details.create

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.CreateConfigurationRequest
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service

interface ServiceConfigurationCreatingHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class CreateConfiguration(
            val service: Service,
            val request: CreateConfigurationRequestData? = null,
        ) : Configuration()

        @Parcelize
        data class CreateConfigurationLoading(val request: CreateConfigurationRequest) :
            Configuration()

        @Parcelize
        data class UnknownError(
            val request: CreateConfigurationRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class CreateConfiguration(
            val component: ServiceConfigurationCreating,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }

}