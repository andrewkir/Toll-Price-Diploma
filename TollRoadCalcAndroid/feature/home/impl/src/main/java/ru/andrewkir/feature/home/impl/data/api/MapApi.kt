package ru.andrewkir.feature.home.impl.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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
    ): Response<List<RouteResponse>>

    @POST("api/more_routes/")
    @NeedAuthentication
    suspend fun getMoreRoutes(
        @Header("Cookie") sessionId: String?,
        @Body routeInfo: RouteRequest
    ): Response<List<RouteResponse>>
}