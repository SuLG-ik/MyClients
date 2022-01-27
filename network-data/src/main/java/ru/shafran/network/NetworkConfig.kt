package ru.shafran.network

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class NetworkConfig(
    val isDebugMode: Boolean,
    val url: String,
    val apiVersion: String,
): Parcelable