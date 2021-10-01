package ru.shafran.network.data.card
import kotlinx.serialization.Serializable

@Serializable
data class UsageData(val employeeId: Long? = null, val note: String = "")