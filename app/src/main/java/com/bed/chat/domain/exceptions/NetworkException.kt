package com.bed.chat.domain.exceptions

import android.content.Context

enum class NetworkMessage(val message: String) {
    UNKNOWN_ERROR("Ocorreu um erro inesperado. Por favor, tente novamente."),
    UNAUTHORIZED("Você não tem permissão para acessar essa funcionalidade."),
    SERVER_ERROR("Algo deu errado no servidor. Tente novamente mais tarde."),
    NOT_FOUND("Oops! O recurso que você está procurando não foi encontrado."),
    BAD_REQUEST("Houve um problema com a sua solicitação. Verifique e tente novamente."),
    UNPROCESSABLE_ENTITY("Alguns dados enviados estão incorretos. Por favor, revise e tente novamente."),
}

sealed class NetworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    class NotFoundException(
        cause: Throwable? = null,
        message: String = NetworkMessage.NOT_FOUND.message
    ) : NetworkException(message, cause)
    class UnknownException(
        cause: Throwable? = null,
        message: String = NetworkMessage.UNKNOWN_ERROR.message
    ) : NetworkException(message, cause)
    class BadRequestException(
        cause: Throwable? = null,
        message: String = NetworkMessage.BAD_REQUEST.message
    ) : NetworkException(message, cause)
    class ServerErrorException(
        cause: Throwable? = null,
        message: String = NetworkMessage.SERVER_ERROR.message
    ) : NetworkException(message, cause)
    class UnprocessableEntityException(
        cause: Throwable? = null,
        message: String = NetworkMessage.UNPROCESSABLE_ENTITY.message
    ) : NetworkException(message, cause)
    class UnauthorizedException(
        cause: Throwable? = null,
        message: String = NetworkMessage.UNAUTHORIZED.message
    ) : NetworkException(message, cause)

    override fun toString(): String = "${this::class.simpleName}: $message"
}
