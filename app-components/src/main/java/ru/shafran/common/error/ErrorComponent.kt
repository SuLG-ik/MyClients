package ru.shafran.common.error

internal class ErrorComponent(
    override val message: Int,
    override val icon: Int,
    private val onContinue: () -> Unit,
) : Error {

    override fun onContinue() {
        onContinue.invoke()
    }

}