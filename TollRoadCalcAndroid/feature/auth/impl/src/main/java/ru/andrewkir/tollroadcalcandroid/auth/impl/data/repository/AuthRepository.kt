package ru.andrewkir.tollroadcalcandroid.auth.impl.data.repository

import ru.andrewkir.core.domain.interactor.TokensInteractor
import ru.andrewkir.core.domain.interactor.TokensInteractorImpl
import ru.andrewkir.tollroadcalcandroid.auth.impl.data.api.AuthApi
import ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models.LoginRequest
import ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models.RegisterRequest

class AuthRepository(
    private val api: AuthApi,
    private val tokensInteractor: TokensInteractor,
) {

    suspend fun register(email: String, username: String, password: String) =
        api.register(
            RegisterRequest(
                email = email,
                username = username,
                password = password,
            )
        )

    suspend fun login(email: String, password: String) {
        val tokens = api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
        tokensInteractor.accessToken = tokens.accessToken
        tokensInteractor.refreshToken = tokens.refreshToken
    }

    fun isUserAuthorized(): Boolean =
        tokensInteractor.accessToken != null

    fun logout() =
        tokensInteractor.logout()
}