package ru.shafran.network.data.employee

import kotlinx.serialization.Serializable

@Serializable
data class ImageInfo(
    val id: Long,
    val employeeId: Long,
    val data: ImageData,
)

