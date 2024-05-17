package ru.andrewkir.feature.home.impl.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.andrewkir.core.network.annotations.NeedAuthentication
import ru.andrewkir.feature.home.impl.domain.models.Locations

interface SearchApi {

    @GET("api/items/geocode/")
    @NeedAuthentication
    suspend fun searchLocation(
        @Query("q") query: String,
        @Query("fields") fields: String,
        @Query("locale") locale: String,
    ): Locations
}