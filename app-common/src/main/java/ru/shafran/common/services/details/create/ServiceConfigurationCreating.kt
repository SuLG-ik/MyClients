package ru.shafran.common.services.details.create

interface ServiceConfigurationCreating {
    val onBack: () -> Unit
    val editor: ServiceConfigurationCreator
}