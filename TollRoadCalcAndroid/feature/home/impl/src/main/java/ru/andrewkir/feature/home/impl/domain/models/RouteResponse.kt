package ru.andrewkir.feature.home.impl.domain.models

data class RouteResponse(
    val total_distance: Int,
    val total_duration: Int,
    val points: List<PointsWithColor>,
    val total_cost: Int?,
    val toll_roads: List<TollRoad>,
)

data class PointsWithColor(
    val color: String,
    val selection: List<Point>,
)

data class TollRoad(
    val tcp_list: List<Point>,
    val cost: Int?,
)