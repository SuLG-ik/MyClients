package ru.shafran.common.auth.login

import ru.shafran.network.auth.data.LoginAccountRequest

class AuthLoginComponent(override val onLogin: (LoginAccountRequest) -> Unit) : AuthLogin