package ru.shafran.network.auth

import ru.shafran.network.auth.data.LoginAccountRequest
import ru.shafran.network.auth.data.LoginAccountResponse

interface AuthenticationRepository {

    suspend fun login(request: LoginAccountRequest): LoginAccountResponse

}