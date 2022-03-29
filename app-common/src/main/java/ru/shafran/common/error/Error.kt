package ru.shafran.common.error

interface Error {

    val message: Int
    val icon: Int

    val onContinue: (() -> Unit)?

}