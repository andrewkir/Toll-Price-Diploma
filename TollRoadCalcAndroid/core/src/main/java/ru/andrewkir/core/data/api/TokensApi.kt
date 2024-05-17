package ru.andrewkir.core.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.andrewkir.core.domain.models.AccessTokenModel
import ru.andrewkir.core.domain.models.RefreshTokenModel
import ru.andrewkir.core.domain.models.TokensModel

interface TokensApi {

    @POST("api/token/verify/")
    suspend fun verifyToken(
        @Body accessTokenModel: AccessTokenModel
    ): Response<Unit>

    @POST("api/token/refresh/")
    suspend fun refreshToken(
        @Body refreshTokenModel: RefreshTokenModel
    ): TokensModel
}