package ru.shafran.common.auth.type

interface AuthType {

    val onLoginUsernameAndPasswordLogin: () -> Unit

}