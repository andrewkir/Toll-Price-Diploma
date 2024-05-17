package ru.andrewkir.core.domain.models

data class JwtPayload(
    val token_type: String,
    val exp: Int,
    val iat: Int,
    val jti: String,
    val user_id: Int,
    val username: String,
    val email: String
)