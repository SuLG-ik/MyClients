package ru.shafran.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NetworkException : Exception()

@Serializable
@SerialName("bad_request_data")
class BadRequest : NetworkException()

@Serializable
@SerialName("illegal_id")
class IllegalId(val field: String, val id: String) : NetworkException()

@Serializable
@SerialName("customer_not_exists")
class CustomerNotExists(val customerId: String) : NetworkException()

@Serializable
@SerialName("card_not_exists")
class CardNotExistsWithId(val cardId: String) : NetworkException()

@Serializable
@SerialName("card_not_exist_with_customer")
class CardNotExistsForCustomer(val customerId: String) : NetworkException()

@Serializable
@SerialName("card_illegal")
class IllegalCardToken(val cardToken: String) : NetworkException()

@Serializable
@SerialName("customer_not_activated")
class CustomerNotActivated(val customerId: String) : NetworkException()

@Serializable
@SerialName("illegal_phone_number")
class IllegalPhoneNumber(val number: String) : NetworkException()

@Serializable
@SerialName("employee_not_exists")
class EmployeeNotExistsWithId(
    val employeeId: String,
) : NetworkException()

@Serializable
@SerialName("service_not_exists")
class ServiceNotExists(
    val serviceId: String,
) : NetworkException()

@Serializable
@SerialName("configuration_not_exists")
class ConfigurationNotExists(
    val configurationId: String,
) : NetworkException()

@Serializable
@SerialName("session_overuse")
class SessionOveruseException(
    val sessionId: String,
) : NetworkException()

@Serializable
@SerialName("session_not_exists")
class SessionNotExists(
    val sessionId: String,
) : NetworkException()

@Serializable
@SerialName("session_deactivated")
class SessionIsDeactivated(
    val sessionId: String,
) : NetworkException()

@Serializable
@SerialName("session_used")
class SessionIsAlreadyUsed(
    val sessionId: String,
) : NetworkException()

@Serializable
@SerialName("business_not_exists")
class BusinessNotExists(
    val businessId: String,
) : NetworkException()

@Serializable
@SerialName("account_not_exists")
class AccountNotExists(val accountId: String) : NetworkException()


@Serializable
@SerialName("account_already_deactivated")
class AccountAlreadyDeactivated(val accountId: String) : NetworkException()

@Serializable
@SerialName("account_already_exists")
class AccountAlreadyExists(val login: String) : NetworkException()

@Serializable
@SerialName("account_illegal_credentials")
class AccountIllegalCredentials(val login: String) : NetworkException()


@Serializable
@SerialName("token_illegal")
class IllegalToken() : NetworkException()

@Serializable
@SerialName("token_expired")
class TokenExpired() : NetworkException()

@Serializable
@SerialName("account_session_not_exists")
class AccountSessionNotExists(val sessionId: String) : NetworkException()

@Serializable
@SerialName("access_denied")
class AccessDenied() : NetworkException()