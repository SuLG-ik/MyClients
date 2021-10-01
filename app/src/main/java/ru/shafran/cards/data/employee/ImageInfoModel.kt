package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.employee.ImageInfo

@Parcelize
data class ImageInfoModel(
    val id: Long,
    val employeeId: Long,
    val data: ImageDataModel,
) : Parcelable


fun ImageInfo.toModel(): ImageInfoModel {
    return ImageInfoModel(
        id = id,
        employeeId = employeeId,
        data = data.toModel(),
    )
}

fun ImageInfoModel.toData(): ImageInfo {
    return ImageInfo(
        id = id,
        employeeId = employeeId,
        data = data.toData(),
    )
}