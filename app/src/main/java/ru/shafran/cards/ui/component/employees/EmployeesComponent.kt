package ru.shafran.cards.ui.component.employees

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.shafran.cards.ui.component.employeesdetails.EmployeesDetails
import ru.shafran.cards.ui.component.employeesdetails.EmployeesDetailsComponent
import ru.shafran.cards.ui.component.employeeslist.EmployeesList
import ru.shafran.cards.ui.component.employeeslist.EmployeesListComponent
import ru.shafran.cards.utils.get
import ru.shafran.cards.utils.stores
import ru.shafran.network.employee.EmployeesStore
import ru.shafran.network.employee.SingleEmployeeStore

class EmployeesComponent(componentContext: ComponentContext) : Employees,
    ComponentContext by componentContext {

    private val employeesListStore by instanceKeeper.stores { EmployeesStore(get(), get()) }
    private val singleEmployeeStore by instanceKeeper.stores { SingleEmployeeStore(get(), get()) }

    override val employeesDetails: EmployeesDetails =
        EmployeesDetailsComponent(
            childContext("employees_details"),
            employeesListStore = employeesListStore,
            singleEmployeeStore = singleEmployeeStore,
        )

    override val employeesList: EmployeesList =
        EmployeesListComponent(
            childContext("employees_list"),
            employeesListStore = employeesListStore,
            onCreateEmployee = this::onCreateEmployee,
        )

    override fun onCreateEmployee() {
        employeesDetails.onCreateEmployee()
    }

}