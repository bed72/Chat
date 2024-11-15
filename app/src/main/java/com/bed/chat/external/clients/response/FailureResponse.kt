package com.bed.chat.external.clients.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.chat.domain.models.output.FailureOutputModel

@Serializable
data class FailureResponse(
    @SerialName("code")
    val code: Int,

    @SerialName("message")
    private val _message: String
) {

    val message: String
        get() = message()

    @Suppress("MagicNumber")
    private fun message() = when (code) {
        400 -> "Ops, um erro inesperado aconteceu."
        422 -> "Ops, algo deu errado com os dados enviados."
        401 -> "Ops, vocẽ não tem permissão para acessar esse recurso."
        else -> "Ops, um erro interno aconteceu."
    }
}

fun FailureResponse.toModel() = FailureOutputModel(
    message = message
)
