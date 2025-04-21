package com.bed.chat.domain.exceptions

enum class ValidationMessage(val message: String) {
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

sealed class ValidationException(message: String) : IllegalArgumentException(message) {
    class NameEmpty(
        message: String = ValidationMessage.NAME_EMPTY.message
    ) : ValidationException(message)
    class NameInvalid(
        message: String = ValidationMessage.NAME_INVALID.message
    ) : ValidationException(message)
    class NameMaxLength(
        message: String = ValidationMessage.NAME_MAX_LENGTH.message
    ) : ValidationException(message)
    class NameMinLength(
        message: String = ValidationMessage.NAME_MIN_LENGTH.message
    ) : ValidationException(message)

    class EmailEmpty(
        message: String = ValidationMessage.EMAIL_EMPTY.message
    ) : ValidationException(message)
    class EmailInvalid(
        message: String = ValidationMessage.EMAIL_INVALID.message
    ) : ValidationException(message)

    class PasswordEmpty(
        message: String = ValidationMessage.PASSWORD_EMPTY.message
    ) : ValidationException(message)
    class PasswordInvalid(
        message: String = ValidationMessage.PASSWORD_INVALID.message
    ) : ValidationException(message)
    class PasswordMaxLength(
        message: String = ValidationMessage.PASSWORD_MAX_LENGTH.message
    ) : ValidationException(message)
    class PasswordMinLength(
        message: String = ValidationMessage.PASSWORD_MIN_LENGTH.message
    ) : ValidationException(message)
    class PasswordContainNumber(
        message: String = ValidationMessage.PASSWORD_CONTAIN_NUMBER.message
    ) : ValidationException(message)
    class PasswordContainCapitalLetter(
        message: String = ValidationMessage.PASSWORD_CONTAIN_CAPITAL_LETTER.message
    ) : ValidationException(message)
}
