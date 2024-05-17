package ru.andrewkir.feature.home.impl.data.repository

import ru.andrewkir.feature.home.impl.data.api.MapApi
import ru.andrewkir.feature.home.impl.domain.models.RouteRequest
import ru.andrewkir.feature.home.impl.domain.models.TransportClass

class MapRepository(
    private val api: MapApi,
) {

    suspend fun getPaymentMethods() =
        api.getPaymentMethods()

    suspend fun getRoute(route: RouteRequest) =
        api.getRoute(route)
}