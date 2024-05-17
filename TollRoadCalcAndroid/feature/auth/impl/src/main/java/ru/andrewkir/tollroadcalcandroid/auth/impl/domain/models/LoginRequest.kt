package ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models

data class LoginRequest(
    val email: String,
    val password: String,
)