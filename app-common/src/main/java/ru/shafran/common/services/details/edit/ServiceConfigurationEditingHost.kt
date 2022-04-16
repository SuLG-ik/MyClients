package ru.shafran.common.services.details.edit

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.EditConfigurationRequest

interface ServiceConfigurationEditingHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class EditServiceConfiguration(val request: EditConfigurationRequest?) :
            Configuration()

        @Parcelize
        data class EditServiceConfigurationLoading(val request: EditConfigurationRequest) :
            Configuration()

        @Parcelize
        data class UnknownError(
            val request: EditConfigurationRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class EditConfiguration(
            val component: ServiceConfigurationEditing,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }

}