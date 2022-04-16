package ru.shafran.common.customers.details.sessions.deactivation

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.session.data.DeactivateSessionRequest
import ru.shafran.network.session.data.DeactivateSessionRequestData

interface SessionDeactivationHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class DeactivateSession(val data: DeactivateSessionRequestData?) :
            Configuration()

        @Parcelize
        data class DeactivateSessionLoading(val request: DeactivateSessionRequest) :
            Configuration()

        @Parcelize
        data class UnknownError(
            val request: DeactivateSessionRequest,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class DeactivateSession(
            val component: SessionDeactivating,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()


    }

}