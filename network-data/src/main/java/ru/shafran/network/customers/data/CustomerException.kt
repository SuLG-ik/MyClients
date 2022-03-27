package ru.shafran.network.customers.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CustomersException : Exception() {


    @Serializable
    @SerialName("customer_not_found")
    class CustomerNotFoundException(val customerId: String) : CustomersException()

    @Serializable
    @SerialName("card_not_found")
    class CardNotFoundWithIdException(val cardId: String) : CustomersException()

    @Serializable
    @SerialName("card_not_found_with_customer")
    class CardNotFoundWithCustomerException(val customerId: String) : CustomersException()

    @Serializable
    @SerialName("card_illegal")
    class IllegalCardTokenException(val cardToken: String) : CustomersException()

    @Serializable
    @SerialName("customer_not_activated")
    class CustomerNotActivated(val customerId: String) : CustomersException()

}