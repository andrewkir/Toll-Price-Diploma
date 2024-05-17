package ru.andrewkir.tollroadcalcandroid.auth.impl.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.andrewkir.core.domain.models.TokensModel
import ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models.LoginRequest
import ru.andrewkir.tollroadcalcandroid.auth.impl.domain.models.RegisterRequest

interface AuthApi {

    @POST("api/register/")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    )

    @POST("api/token/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): TokensModel
}