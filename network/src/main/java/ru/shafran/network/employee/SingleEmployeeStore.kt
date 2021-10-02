package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.employee.Employee
import ru.shafran.network.data.employee.EmployeeData

interface SingleEmployeeStore :
    Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> {

    sealed class Intent {
        data class LoadEmployee(val id: Long) : Intent()
        data class CreateEmployee(
            val employeeData: EmployeeData,
        ) : Intent()

        data class AddImageToEmployee(
            val employeeId: Long,
            val image: ByteArray,
        ) : Intent() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as AddImageToEmployee

                if (employeeId != other.employeeId) return false
                if (!image.contentEquals(other.image)) return false

                return true
            }

            override fun hashCode(): Int {
                var result = employeeId.hashCode()
                result = 31 * result + image.contentHashCode()
                return result
            }
        }
    }

    sealed class State {
        object Loading : State()
        object Hidden : State()
        data class EmployeeLoaded(
            val employee: Employee,
        ) : State()
        sealed class Error : State() {
            object NotFoundException : Error()
            object InternalServerError : Error()
            object ConnectionLost: Error()
            object UnknownError : Error()
        }
    }

    sealed class Label {
        object OnUpdatedOrCreated : Label()
    }

}