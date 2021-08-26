package ru.shafran.cards.usecase

import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.employee.Employee
import ru.shafran.cards.data.employee.EmployeeData
import ru.shafran.cards.usecase.cards.param.ActivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.DeactivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.UseCardByIdParam
import ru.sulgik.common.UseCase

interface UseCaseFactory {

    fun cardByToken(): UseCase<Card, String>

    fun activateCard(): UseCase<Unit, ActivateCardByIdParam>

    fun deactivateCard(): UseCase<Unit, DeactivateCardByIdParam>

    fun useCard(): UseCase<Unit, UseCardByIdParam>

    fun createEmployee(): UseCase<Unit, EmployeeData>

    fun getAllEmployees(): UseCase<List<Employee>, Unit>

    fun getEmployeeById(): UseCase<Employee, Long>

}