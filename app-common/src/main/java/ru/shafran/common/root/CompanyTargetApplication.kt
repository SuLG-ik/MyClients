package ru.shafran.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.companies.data.Company

interface CompanyTargetApplication {

    val company: Company

    val routerState: Value<RouterState<Configuration, Child<Any?>>>

    fun onNavigate(configuration: Configuration)

    sealed class Child<out T> {

        abstract val component: T

        class CustomerScanner(
            override val component: ru.shafran.common.scanner.CustomerScanner,
        ) :
            Child<ru.shafran.common.scanner.CustomerScanner>()

        class Services(
            override val component: ru.shafran.common.services.Services,
        ) : Child<ru.shafran.common.services.Services>()


        class Customers(
            override val component: ru.shafran.common.customers.Customers,
        ) : Child<ru.shafran.common.customers.Customers>()

        class Employees(
            override val component: ru.shafran.common.employees.Employees,
        ) : Child<ru.shafran.common.employees.Employees>()

    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object Services : Configuration()

        @Parcelize
        object CustomerScanner : Configuration()

        @Parcelize
        object Customers : Configuration()

        @Parcelize
        object Employees : Configuration()

    }

}