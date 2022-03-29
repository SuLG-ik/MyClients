package ru.shafran.common.scanner

import ru.shafran.common.camera.Camera
import ru.shafran.common.customers.details.host.CustomerDetailsHost

interface CustomerScanner {

    val customerDetails: CustomerDetailsHost

    val camera: Camera

    fun onGenerateCustomer()

    fun onSearch()

}