package ru.shafran.common.services.details.edit

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.EditServiceRequest
import ru.shafran.network.services.data.EditableServiceData

interface ServiceEditingHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class EditService(val request: EditableServiceData) : Configuration()

        @Parcelize
        data class EditServiceLoading(val request: EditServiceRequest) : Configuration()

        @Parcelize
        data class UnknownError(
            val request: EditServiceRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class EditService(
            val component: ServiceEditing,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }

}