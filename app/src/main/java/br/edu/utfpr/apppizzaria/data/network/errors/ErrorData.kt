package br.edu.utfpr.apppizzaria.data.network.errors;

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    val title: String = "",
    val message: String = "",
    val statusCode: Int = 500,
    val fieldErrors: List<ErrorField> = listOf()
)
