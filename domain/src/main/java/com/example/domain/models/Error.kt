package com.example.domain.models


import io.ktor.client.plugins.*
import java.io.IOException

sealed class Error {
    class Server(val code: Int) : Error()
    object Connectivity : Error()
    class Unknown(val message: String) : Error()
}

fun Exception.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is ResponseException -> Error.Server(this.response.status.value)
    else -> Error.Unknown(message ?: "Unknown Error")
}