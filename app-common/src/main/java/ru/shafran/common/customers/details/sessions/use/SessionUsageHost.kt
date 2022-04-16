package ru.shafran.common.customers.details.sessions.use

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.session.data.Session

interface SessionUsageHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Empty : Configuration()

        @Parcelize
        object DetailsLoading : Configuration()

        @Parcelize
        data class Loaded(
            val session: Session,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Loaded(
            val component: SessionUsing,
        ) : Child()

    }

}