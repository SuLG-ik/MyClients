package ru.shafran.cards.utils


sealed class Task<out T> {

    abstract val isSuccess: Boolean

    abstract val isFailure: Boolean

    abstract val isLoading: Boolean

    abstract fun getOrNull(): T?

    abstract fun exceptionOrNull(): Throwable?

    companion object {
        fun <T> success(value: T): Task<T> =
            Success(value = value)

        fun <T> failure(exception: Throwable): Task<T> =
            Failure(exception = exception)

        fun <T> loading(): Task<T> = Loading

    }

    class Success<T>(
        val value: T
    ): Task<T>() {
        override val isSuccess: Boolean = true
        override val isFailure: Boolean = false
        override val isLoading: Boolean = false

        override fun getOrNull(): T {
            return value
        }

        override fun exceptionOrNull(): Throwable? = null
    }

    object Loading: Task<Nothing>() {
        override val isSuccess: Boolean = false

        override val isFailure: Boolean = false
        override val isLoading: Boolean = true

        override fun getOrNull(): Nothing? = null
        override fun exceptionOrNull(): Throwable? = null
    }

    class Failure<T>(
        val exception: Throwable
    ) : Task<T>() {

        override val isSuccess: Boolean = false

        override val isFailure: Boolean = true
        override val isLoading: Boolean = false

        override fun getOrNull(): T? = null
        override fun exceptionOrNull(): Throwable = exception
    }
}