package ru.andrewkir.feature.home.impl.data.repository

import ru.andrewkir.feature.home.impl.data.api.MapApi
import ru.andrewkir.feature.home.impl.domain.models.RouteRequest

class MapRepository(
    private val api: MapApi,
) {

    suspend fun getPaymentMethods() =
        api.getPaymentMethods()

    suspend fun getRoute(route: RouteRequest) =
        api.getRoute(route)

    suspend fun getMoreRoutes(route: RouteRequest, sessionId: String?) =
        api.getMoreRoutes(sessionId, route)
}