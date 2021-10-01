package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.employee.ImageData
import java.time.ZonedDateTime

@Parcelize
data class ImageDataModel(
    val url: String,
    val date: ZonedDateTime,
) : Parcelable

fun ImageData.toModel(): ImageDataModel {
    return ImageDataModel(
        url = url,
        date = date,
    )
}

fun ImageDataModel.toData(): ImageData {
    return ImageData(
        url = url,
        date = date,
    )
}