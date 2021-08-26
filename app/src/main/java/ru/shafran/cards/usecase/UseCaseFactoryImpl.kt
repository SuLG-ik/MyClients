package ru.shafran.cards.usecase

import io.ktor.client.*
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.employee.Employee
import ru.shafran.cards.data.employee.EmployeeData
import ru.shafran.cards.network.NetworkConfig
import ru.shafran.cards.usecase.cards.ActivateCardByIdUseCase
import ru.shafran.cards.usecase.cards.DeactivateCardByIdUseCase
import ru.shafran.cards.usecase.cards.GetCardByTokenUseCase
import ru.shafran.cards.usecase.cards.UseCardByIdUseCase
import ru.shafran.cards.usecase.cards.param.ActivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.DeactivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.UseCardByIdParam
import ru.shafran.cards.usecase.employees.CreateEmployeeUseCase
import ru.shafran.cards.usecase.employees.GetAllEmployeesUseCase
import ru.shafran.cards.usecase.employees.GetEmployeeByIdUseCase
import ru.sulgik.common.UseCase

class UseCaseFactoryImpl(
    val client: HttpClient,
    val networkConfig: NetworkConfig,
) : UseCaseFactory {
    override fun cardByToken(): UseCase<Card, String> {
        return GetCardByTokenUseCase(
            client,
            networkConfig,
        )
    }

    override fun activateCard(): UseCase<Unit, ActivateCardByIdParam> {
        return ActivateCardByIdUseCase(
            client,
            networkConfig,
        )
    }

    override fun deactivateCard(): UseCase<Unit, DeactivateCardByIdParam> {
        return DeactivateCardByIdUseCase(
            client,
            networkConfig,
        )
    }

    override fun useCard(): UseCase<Unit, UseCardByIdParam> {
        return UseCardByIdUseCase(
            client,
            networkConfig,
        )
    }

    override fun createEmployee(): UseCase<Unit, EmployeeData> {
        return CreateEmployeeUseCase(
            client,
            networkConfig,
        )
    }

    override fun getAllEmployees(): UseCase<List<Employee>, Unit> {
        return GetAllEmployeesUseCase(
            client,
            networkConfig,
        )
    }

    override fun getEmployeeById(): UseCase<Employee, Long> {
        return GetEmployeeByIdUseCase(
            client,
            networkConfig,
        )
    }
}