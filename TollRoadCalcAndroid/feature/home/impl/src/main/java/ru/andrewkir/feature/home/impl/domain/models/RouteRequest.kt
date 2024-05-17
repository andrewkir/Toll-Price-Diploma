package ru.andrewkir.feature.home.impl.domain.models

data class RouteRequest(
    val points: List<Point>,
    val payment_methods: List<String>,
    val vehicle_class: Int,
    val utc: Long?,
)

data class Point(
    val lon: Double,
    val lat: Double,
)