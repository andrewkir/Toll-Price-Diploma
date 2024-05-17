package ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)