package com.ajeeb.bumblecar.common.domain.utils

sealed interface Issues : GenericError {

    /**Errors that can occur while performing network calls*/
    sealed class Network(open val message: String? = null) : Issues {
        data class NoResponse(override val message: String? = null) : Network(message)
        data class NoInternet(override val message: String? = null) : Network(message)
        data class InternalError(override val message: String? = null) : Network(message)
        data class RequestTimeOut(override val message: String? = null) : Network(message)
        data class PayloadTooLarge(override val message: String? = null) : Network(message)
        data class Unknown(override val message: String? = null) : Network(message)
        data class UnableToUpload(override val message: String? = null) : Network(message)
        data class FileNotFound(override val message: String? = null) : Network(message)
        data class ConflictFound(override val message: String? = null, val type: String? = null) :
            Network(message)

        data class UnAuthorized(override val message: String? = null) : Network(message)
        data class BadRequest(override val message: String? = null) : Network(message)
        data class MappingFailed(override val message: String? = null) : Network(message)
        data class AccountLocked(override val message: String? = null) : Network(message)
        data class UnprocessableEntity(override val message: String? = null) : Network(message)
    }
}