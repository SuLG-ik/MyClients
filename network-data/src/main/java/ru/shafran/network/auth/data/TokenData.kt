package ru.shafran.network.auth.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AccessTokenData : Parcelable

@Serializable
@SerialName("jwt")
@Parcelize
data class JwtAccessTokenData(val accessToken: String) : AccessTokenData()

@Serializable
sealed class RefreshTokenData : Parcelable

@Serializable
@SerialName("jwt")
@Parcelize
data class JwtRefreshTokenData(val refreshToken: String) : RefreshTokenData()

@Serializable
sealed class TokenData: Parcelable

@Serializable
@SerialName("jwt")
@Parcelize
class JwtTokenData(
    val accessToken: JwtAccessTokenData,
    val refreshToken: JwtRefreshTokenData,
    val expiresAt: Long,
    val sessionId: String,
    val accountId: String,
) : TokenData()