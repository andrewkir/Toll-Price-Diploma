package ru.andrewkir.core.domain.models

import com.squareup.moshi.Json

data class RefreshTokenModel(

    @Json(name = "refresh")
    val refreshToken: String
)