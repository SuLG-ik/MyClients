package ru.shafran.network.session.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UseSessionRequest(
    val sessionId: String,
    val employeeId: String,
    val note: String? = null,
): Parcelable

@Serializable
@Parcelize
data class UseSessionResponse(
    val session: Session,
): Parcelable

@Parcelize
@Serializable
data class DeactivateSessionRequest(
    val sessionId: String,
    val data: DeactivateSessionRequestData,
) : Parcelable

@Serializable
@Parcelize
data class DeactivateSessionRequestData(
    val employeeId: String,
    val note: String?,
    val reason: SessionManualDeactivationReason,
) : Parcelable

@Parcelize
@Serializable
data class DeactivateSessionResponse(
    val session: Session,
) : Parcelable
