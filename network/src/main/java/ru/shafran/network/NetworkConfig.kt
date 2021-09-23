package ru.shafran.network

data class NetworkConfig(
    val isDebugMode: Boolean,
    val host: String,
    val protocol: String,
    val port: Int,
    val apiVersion: String,
)