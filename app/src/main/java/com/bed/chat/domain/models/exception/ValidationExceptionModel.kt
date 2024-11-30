package com.bed.chat.domain.models.exception

enum class ValidationMessagesModel(val message: String) {
    NAME_INVALID("Nome inválido."),
    NAME_EMPTY("O nome não pode ser vazio."),
    NAME_MAX_LENGTH("O nome deve ter até 12 caracteres."),
    NAME_MIN_LENGTH("O nome deve ter pelo menos 2 caracteres."),

    EMAIL_INVALID("E-mail inválido."),
    EMAIL_EMPTY("O e-mail não pode ser vazio."),

    PASSWORD_INVALID("Senha inválida."),
    PASSWORD_EMPTY("A senha não pode ser vazia."),
    PASSWORD_MAX_LENGTH("A senha deve ter até 16 caracteres."),
    PASSWORD_MIN_LENGTH("A senha deve ter pelo menos 6 caracteres."),
    PASSWORD_CONTAIN_NUMBER("A senha deve conter pelo menos um número."),
    PASSWORD_CONTAIN_CAPITAL_LETTER("A senha deve conter pelo menos uma letra maiúscula."),
}

sealed class ValidationExceptionModel(message: String) : IllegalArgumentException(message) {
    class NameEmpty(
        message: String = ValidationMessagesModel.NAME_EMPTY.message
    ) : ValidationExceptionModel(message)
    class NameInvalid(
        message: String = ValidationMessagesModel.NAME_INVALID.message
    ) : ValidationExceptionModel(message)
    class NameMaxLength(
        message: String = ValidationMessagesModel.NAME_MAX_LENGTH.message
    ) : ValidationExceptionModel(message)
    class NameMinLength(
        message: String = ValidationMessagesModel.NAME_MIN_LENGTH.message
    ) : ValidationExceptionModel(message)

    class EmailEmpty(
        message: String = ValidationMessagesModel.EMAIL_EMPTY.message
    ) : ValidationExceptionModel(message)
    class EmailInvalid(
        message: String = ValidationMessagesModel.EMAIL_INVALID.message
    ) : ValidationExceptionModel(message)

    class PasswordEmpty(
        message: String = ValidationMessagesModel.PASSWORD_EMPTY.message
    ) : ValidationExceptionModel(message)
    class PasswordInvalid(
        message: String = ValidationMessagesModel.PASSWORD_INVALID.message
    ) : ValidationExceptionModel(message)
    class PasswordMaxLength(
        message: String = ValidationMessagesModel.PASSWORD_MAX_LENGTH.message
    ) : ValidationExceptionModel(message)
    class PasswordMinLength(
        message: String = ValidationMessagesModel.PASSWORD_MIN_LENGTH.message
    ) : ValidationExceptionModel(message)
    class PasswordContainNumber(
        message: String = ValidationMessagesModel.PASSWORD_CONTAIN_NUMBER.message
    ) : ValidationExceptionModel(message)
    class PasswordContainCapitalLetter(
        message: String = ValidationMessagesModel.PASSWORD_CONTAIN_CAPITAL_LETTER.message
    ) : ValidationExceptionModel(message)
}
