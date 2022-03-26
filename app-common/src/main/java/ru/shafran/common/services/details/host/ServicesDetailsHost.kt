package ru.shafran.common.services.details.host

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.services.details.info.ServiceInfoHost
import ru.shafran.network.services.data.Service

interface ServicesDetailsHost {

    val isShown: Value<Boolean>

    fun onShowServiceDetails(service: Service)

    fun onShowServiceDetails(serviceId: String)

    fun onHide()

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

    }

    sealed class Child<out T> {

        abstract val component: T

        object Hidden : Child<Nothing?>() {
            override val component: Nothing?
                get() = null
        }

        data class ServiceInfo(
            override val component: ServiceInfoHost,
        ) : Child<ServiceInfoHost>()

    }

}