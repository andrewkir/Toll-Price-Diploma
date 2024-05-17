package ru.andrewkir.feature.home.impl.domain.interactor

import ru.andrewkir.feature.home.impl.data.repository.MapRepository
import ru.andrewkir.feature.home.impl.domain.models.Point
import ru.andrewkir.feature.home.impl.domain.models.RouteRequest

class MapInteractor(private val repository: MapRepository) {

    suspend fun getRoute(
        pointA: Point,
        pointB: Point,
        startTime: Long?,
        transportClass: Int,
        paymentMethod: String
    ) =
        repository.getRoute(
            RouteRequest(
                points = listOf(pointA, pointB),
                utc = startTime,
                vehicle_class = transportClass,
                payment_methods = listOf(paymentMethod)
            )
        )

    suspend fun getPaymentMethods() =
        repository.getPaymentMethods()
}