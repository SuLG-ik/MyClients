package ru.shafran.network.auth

import ru.shafran.network.auth.data.TokenData

interface AuthenticationStorage {

    suspend fun getAuthorizedToken(): TokenData?

    suspend fun setAuthorizedToken(token: TokenData)

}