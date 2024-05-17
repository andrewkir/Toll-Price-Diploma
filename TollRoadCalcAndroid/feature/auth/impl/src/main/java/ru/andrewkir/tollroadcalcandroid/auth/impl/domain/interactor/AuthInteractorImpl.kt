package ru.andrewkir.tollroadcalcandroid.auth.impl.domain.interactor

import ru.andrewkir.tollroadcalcandroid.auth.api.interactor.AuthInteractor
import ru.andrewkir.tollroadcalcandroid.auth.impl.data.repository.AuthRepository

class AuthInteractorImpl(
    private val repository: AuthRepository
) : AuthInteractor {
    override suspend fun login(email: String, password: String) =
        repository.login(email, password)

    override suspend fun register(email: String, username: String, password: String) =
        repository.register(email, username, password)

    override fun isUserAuthorized(): Boolean =
        repository.isUserAuthorized()

    override fun logout() =
        repository.logout()
}