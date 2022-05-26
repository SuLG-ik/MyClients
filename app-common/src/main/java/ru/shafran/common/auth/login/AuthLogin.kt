package ru.shafran.common.auth.login

import ru.shafran.network.auth.data.LoginAccountRequest

interface AuthLogin {

    val onLogin: (LoginAccountRequest) -> Unit

}