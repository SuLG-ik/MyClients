package ru.shafran.common.error

internal class ErrorComponent(
    override val message: Int,
    override val icon: Int,
    override val onContinue: (() -> Unit)? = null,
) : Error {


}