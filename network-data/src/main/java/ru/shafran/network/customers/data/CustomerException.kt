package beauty.shafran.network.customers.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CustomersException : Exception() {

    @Serializable
    @SerialName("customer_not_found")
    class CustomerNotFoundException(val customerId: String) : CustomersException()

    @Serializable
    @SerialName("card_not_found")
    class CardNotFoundException(val cardId: String) : CustomersException()
    @Serializable
    @SerialName("card_illegal")
    class IllegalCardTokenException(val cardToken: String) : CustomersException()

}