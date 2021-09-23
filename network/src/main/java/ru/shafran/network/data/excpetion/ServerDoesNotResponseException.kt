package ru.shafran.network.data.excpetion

class ServerDoesNotResponseException (
    private val status: Int
        ): Exception()