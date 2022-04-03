package ru.shafran.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ShafranNetworkException : Exception()

@Serializable
@SerialName("bad_request_data")
class BadRequest() : ShafranNetworkException()

@Serializable
@SerialName("illegal_id")
class IllegalId(
    val field: String,
    val id: String,
) : ShafranNetworkException()

@Serializable
@SerialName("customer_not_exists")
class CustomerNotExists(val customerId: String) : ShafranNetworkException()

@Serializable
@SerialName("card_not_exists")
class CardNotExistsWithId(val cardId: String) : ShafranNetworkException()

@Serializable
@SerialName("card_not_exist_with_customer")
class CardNotExistsForCustomer(val customerId: String) : ShafranNetworkException()

@Serializable
@SerialName("card_illegal")
class IllegalCardToken(val cardToken: String) : ShafranNetworkException()

@Serializable
@SerialName("customer_not_activated")
class CustomerNotActivated(val customerId: String) : ShafranNetworkException()

@Serializable
@SerialName("illegal_phone_number")
class IllegalPhoneNumber(val number: String) : ShafranNetworkException()

@Serializable
@SerialName("illegal_request")
class IllegalRequest(
    val fieldName: String,
    override val message: String,
) : ShafranNetworkException()

@Serializable
@SerialName("employee_not_exists")
class EmployeeNotExistsWithId(
    val employeeId: String,
) : ShafranNetworkException()

@Serializable
@SerialName("service_not_exists")
class ServiceNotExists(
    val serviceId: String,
) : ShafranNetworkException()

@Serializable
@SerialName("configuration_not_exists")
class ConfigurationNotExists(
    val configurationId: String,
) : ShafranNetworkException()

@Serializable
@SerialName("session_overuse")
class SessionOveruseException(
    val sessionId: String,
) : ShafranNetworkException()


@Serializable
@SerialName("session_not_exists")
class SessionNotExists(
    val sessionId: String,
) : ShafranNetworkException()