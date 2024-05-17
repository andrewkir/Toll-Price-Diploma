package ru.andrewkir.core.domain.models

import com.squareup.moshi.Json

data class AccessTokenModel(

    @Json(name = "token")
    val token: String
)