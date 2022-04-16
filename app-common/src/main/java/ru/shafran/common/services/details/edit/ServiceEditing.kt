package ru.shafran.common.services.details.edit

interface ServiceEditing {
    val editor: ServiceEditor
    val onBack: () -> Unit
}