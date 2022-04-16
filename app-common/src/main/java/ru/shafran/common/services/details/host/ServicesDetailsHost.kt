package ru.shafran.common.services.details.host

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.services.details.info.ServiceInfoHost
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

interface ServicesDetailsHost {

    val isShown: Value<Boolean>

    val onShowServiceDetails: (service: Service) -> Unit

    val onShowServiceDetailsById: (serviceId: String) -> Unit

    val onCreateService: () -> Unit

    val onHide: () -> Unit

    val routerState: Value<RouterState<Configuration, Child<Any?>>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Hidden : Configuration()

        @Parcelize
        data class ServiceInfoById(
            val serviceId: String,
        ) : Configuration()

        @Parcelize
        data class ServiceInfoByData(
            val service: Service,
        ) : Configuration()

        @Parcelize
        data class EditConfiguration(
            val configuration: ConfiguredService,
        ) : Configuration()

        @Parcelize
        data class EditService(
            val service: Service,
        ) : Configuration()

        @Parcelize
        data class CreateConfiguration(
            val service: Service,
        ) : Configuration()

        @Parcelize
        object CreateService : Configuration()

    }

    sealed class Child<out T> {

        abstract val component: T

        object Hidden : Child<Nothing?>() {
            override val component: Nothing?
                get() = null
        }

        class ServiceInfo(
            override val component: ServiceInfoHost,
        ) : Child<ServiceInfoHost>()

        class CreateService(
            override val component: ru.shafran.common.services.details.create.ServiceCreatingHost,
        ) : Child<ru.shafran.common.services.details.create.ServiceCreatingHost>()

        class EditService(
            override val component: ru.shafran.common.services.details.edit.ServiceEditingHost,
        ) : Child<ru.shafran.common.services.details.edit.ServiceEditingHost>()


        class CreateConfiguration(
            override val component: ru.shafran.common.services.details.create.ServiceConfigurationCreatingHost,
        ) : Child<ru.shafran.common.services.details.create.ServiceConfigurationCreatingHost>()


        class EditConfiguration(
            override val component: ru.shafran.common.services.details.edit.ServiceConfigurationEditing,
        ) : Child<ru.shafran.common.services.details.edit.ServiceConfigurationEditing>()

    }

    val onEditService: (service: Service) -> Unit
    val onCreateConfiguration: (service: Service) -> Unit
}