package ru.sulgik.common

interface UseCase <T, P> {

    suspend fun run(parameter: P) : T

}