package ru.shafran.common.employees.details.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.companies.data.Company
import ru.shafran.network.employees.CreateEmployeeStore
import ru.shafran.network.employees.data.CreateEmployeeRequestData
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class EmployeeCreatingHostComponent(
    componentContext: ComponentContext,
    private val onEmployeeCreated: (Employee) -> Unit,
    private val company: Company,
) : EmployeeCreatingHost, ComponentContext by componentContext {

    private val store = getStore<CreateEmployeeStore>()
        .reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)


    private fun reduceLabel(label: CreateEmployeeStore.Label) {
        when (label) {
            is CreateEmployeeStore.Label.OnEmployeeCreated -> onEmployeeCreated(label.employee)
        }
    }

    private fun reduceState(state: CreateEmployeeStore.State) {
        when (state) {
            is CreateEmployeeStore.State.CreateEmployee ->
                router.replaceAll(EmployeeCreatingHost.Configuration.CreateEmployee(state.request))
            is CreateEmployeeStore.State.CreateEmployeeLoading ->
                router.replaceAll(EmployeeCreatingHost.Configuration.CreateEmployeeLoading(state.request))
            is CreateEmployeeStore.State.Error.Unknown ->
                router.replaceAll(EmployeeCreatingHost.Configuration.UnknownError(state.request))
            is CreateEmployeeStore.State.Empty ->
                onUpdate(null)
        }
    }

    private val onUpdate: (CreateEmployeeRequestData?) -> Unit = {
        store.accept(CreateEmployeeStore.Intent.LoadDetails(it))
    }

    private val router = router<EmployeeCreatingHost.Configuration, EmployeeCreatingHost.Child>(
        initialConfiguration = EmployeeCreatingHost.Configuration.CreateEmployee(null),
        childFactory = this::createChild
    )

    private val onCreateEmployee: (CreateEmployeeRequestData) -> Unit = {
        store.accept(CreateEmployeeStore.Intent.CreateEmployee(data = it, companyId = company.id))
    }

    private fun createChild(
        configuration: EmployeeCreatingHost.Configuration,
        componentContext: ComponentContext,
    ): EmployeeCreatingHost.Child {
        return when (configuration) {
            is EmployeeCreatingHost.Configuration.CreateEmployee ->
                EmployeeCreatingHost.Child.CreateEmployee(
                    EmployeeCreatingComponent(
                        data = configuration.request,
                        onApply = { onCreateEmployee(it) }
                    )
                )
            is EmployeeCreatingHost.Configuration.CreateEmployeeLoading ->
                EmployeeCreatingHost.Child.Loading(
                    LoadingComponent(
                        R.string.employee_creating,
                    )
                )
            is EmployeeCreatingHost.Configuration.UnknownError ->
                EmployeeCreatingHost.Child.Error(
                    ErrorComponent(
                        R.string.unknwon_error,
                        R.drawable.error,
                        onContinue = { onUpdate(configuration.request.data) }
                    )
                )
        }
    }

    override val routerState: Value<RouterState<EmployeeCreatingHost.Configuration, EmployeeCreatingHost.Child>> =
        router.state
}