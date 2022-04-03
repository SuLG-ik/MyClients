package ru.shafran.common.utils

interface Updatable {

    val onUpdate: (() -> Unit)?

}