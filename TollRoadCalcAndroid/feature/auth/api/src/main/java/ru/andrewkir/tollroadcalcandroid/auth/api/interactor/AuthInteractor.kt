package ru.andrewkir.tollroadcalcandroid.auth.api.interactor

interface AuthInteractor {

    suspend fun login(email: String, password: String)

    suspend fun register(email: String, username: String, password: String)

    fun isUserAuthorized(): Boolean

    fun logout()
}