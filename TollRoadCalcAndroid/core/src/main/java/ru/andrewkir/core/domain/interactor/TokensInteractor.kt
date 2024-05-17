package ru.andrewkir.core.domain.interactor

interface TokensInteractor {

    var accessToken: String?

    var refreshToken: String?

    val username: String?

    val email: String

    fun logout()
    fun isTokenExpired(): Boolean
    fun updateTokens()
}