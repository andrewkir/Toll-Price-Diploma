package ru.andrewkir.core.domain.models

import com.squareup.moshi.Json

data class TokensModel(
    @Json(name = "access")
    val accessToken: String,
    @Json(name = "refresh")
    val refreshToken: String?,
)