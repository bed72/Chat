package com.bed.chat.domain.models.exception

@Suppress("MagicNumber")
enum class NetworkMessagesModel(val status: Int, val message: String) {
    UNKNOWN_ERROR(0, "Ocorreu um erro inesperado. Por favor, tente novamente."),
    UNAUTHORIZED(401, "Você não tem permissão para acessar essa funcionalidade."),
    SERVER_ERROR(500, "Algo deu errado no servidor. Tente novamente mais tarde."),
    NOT_FOUND(404, "Oops! O recurso que você está procurando não foi encontrado."),
    BAD_REQUEST(400, "Houve um problema com a sua solicitação. Verifique e tente novamente."),
    UNPROCESSABLE_ENTITY(422, "Alguns dados enviados estão incorretos. Por favor, revise e tente novamente."),
}

sealed class NetworkExceptionModel(
    status: Int,
    message: String,
    cause: Throwable? = null
) : Exception("$status - $message", cause) {
    class NotFoundExceptionModel(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.NOT_FOUND.status,
        message: String = NetworkMessagesModel.NOT_FOUND.message
    ) : NetworkExceptionModel(status, message, cause)
    class UnknownExceptionModel(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.UNKNOWN_ERROR.status,
        message: String = NetworkMessagesModel.UNKNOWN_ERROR.message
    ) : NetworkExceptionModel(status, message, cause)
    class BadRequestExceptionModel(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.BAD_REQUEST.status,
        message: String = NetworkMessagesModel.BAD_REQUEST.message
    ) : NetworkExceptionModel(status, message, cause)
    class ServerErrorExceptionModel(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.SERVER_ERROR.status,
        message: String = NetworkMessagesModel.SERVER_ERROR.message
    ) : NetworkExceptionModel(status, message, cause)
    class UnauthorizedExceptionModel(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.UNAUTHORIZED.status,
        message: String = NetworkMessagesModel.UNAUTHORIZED.message
    ) : NetworkExceptionModel(status, message, cause)
    class UnprocessableEntityException(
        cause: Throwable? = null,
        status: Int = NetworkMessagesModel.UNPROCESSABLE_ENTITY.status,
        message: String = NetworkMessagesModel.UNPROCESSABLE_ENTITY.message
    ) : NetworkExceptionModel(status, message, cause)

    override fun toString(): String = "${this::class.simpleName}: $message"
}
