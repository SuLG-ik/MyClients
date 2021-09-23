package ru.sulgik.logger

interface Logger {

    fun debug(tag: String, message: String, throwable: Throwable? = null)

    fun warning(tag: String, message: String, throwable: Throwable? = null)

    fun error(tag: String, message: String, throwable: Throwable? = null)

    fun info(tag: String, message: String, throwable: Throwable? = null)

    fun verbose(tag: String, message: String, throwable: Throwable? = null)

    fun wtf(tag: String, message: String, throwable: Throwable? = null)

}