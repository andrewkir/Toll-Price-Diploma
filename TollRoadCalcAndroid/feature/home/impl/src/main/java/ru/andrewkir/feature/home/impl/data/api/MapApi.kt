package ru.andrewkir.feature.home.impl.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.andrewkir.core.network.annotations.NeedAuthentication
import ru.andrewkir.feature.home.impl.domain.models.RouteRequest
import ru.andrewkir.feature.home.impl.domain.models.RouteResponse

interface MapApi {

    @GET("api/payment_methods/")
    @NeedAuthentication
    suspend fun getPaymentMethods(): Map<String, String>

    @POST("api/routes/")
    @NeedAuthentication
    suspend fun getRoute(
        @Body routeInfo: RouteRequest
    ): List<RouteResponse>
}