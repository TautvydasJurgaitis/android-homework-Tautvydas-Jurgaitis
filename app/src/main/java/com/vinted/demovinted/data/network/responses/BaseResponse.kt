package com.vinted.demovinted.data.network.responses

import java.util.*

open class BaseResponse(
        var code: Int = ResponseCode.OK.code,
        var message: String? = null,
        val messageCode: String? = null,
        var errors: List<ApiValidationError>? = null,
        val payload: Payload? = null
) {

    val isStatusOK: Boolean
        get() = code == ResponseCode.OK.code

    val isValidationError: Boolean
        get() = code == ResponseCode.VALIDATION_ERROR.code || code == ResponseCode.IDENTITY_VERIFICATION_ERROR.code

    val isAccountBanned: Boolean
        get() = code == ResponseCode.INVALID_USER.code && messageCode == MESSAGE_CODE_ACCOUNT_BANNED

    val responseCode: ResponseCode
        get() = ResponseCode[code]

    @Suppress("unused")
    enum class ResponseCode constructor(val code: Int) {
        OK(0),
        NO_PERMISSIONS(1),
        FB_TOKEN_EMPTY(5),
        OBJECT_DOES_NOT_EXIST(15),
        USER_LOGIN_REQUIRED(21),
        PHOTO_UPLOAD_ERROR(50),
        INVALID_USER(10),
        LOGIN_REQUIRED(21),
        VALIDATION_ERROR(99),
        INVALID_TOKEN(100),
        INVALID_PROTOCOL(103),
        NOT_FOUND(104),
        SERVER_ERROR(105),
        ACCESS_DENIED(106),
        LOCK_CONFLICT(107),
        IDENTITY_VERIFICATION_ERROR(108),
        UNAVAILABLE_CARRIER_ERROR(110), // when seller changes carrier options while buyer is in checkout
        CONFIRMATION_REQUIRED(112), // when email is not confirmed
        USER_VERIFICATION_REQUIRED(115),
        DEPRECATED(116),
        FOLLOWING_TOO_MANY_MEMBERS(117),
        USER_2FA_CONFIRMATION(118),
        UNAVAILABLE_PAY_IN_METHOD(119), // Credit card is deleted in the backend
        ACCOUNT_NUMBER_DETECTED(120),
        LOGIN_WITHOUT_EMAIL(121),
        TWO_FACTOR_AUTH_REQUIRED(122),
        CREDIT_CARD_EXPIRED(124), // Credit card data is updated in the backend
        WAIT_FOR_PHONE_CHANGE(125),
        UNKNOWN_ERROR(-1);

        companion object {
            operator fun get(statusCode: Int): ResponseCode = values().find { it.code == statusCode } ?: UNKNOWN_ERROR

            val SHOULD_REFRESH_TOKEN = listOf(USER_LOGIN_REQUIRED, INVALID_USER, INVALID_TOKEN)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as BaseResponse

        if (code != other.code) return false
        if (message != other.message) return false
        if (messageCode != other.messageCode) return false
        if (errors != other.errors) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (messageCode?.hashCode() ?: 0)
        result = 31 * result + (errors?.hashCode() ?: 0)
        return result
    }

    companion object {
        private const val MESSAGE_CODE_ACCOUNT_BANNED = "account_banned"
    }
}
