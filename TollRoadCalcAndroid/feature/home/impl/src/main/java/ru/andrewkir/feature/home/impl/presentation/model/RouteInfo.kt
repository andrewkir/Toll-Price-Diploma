package ru.andrewkir.feature.home.impl.presentation.model

import ru.andrewkir.feature.home.impl.domain.models.PointsWithColor
import ru.andrewkir.feature.home.impl.domain.models.TollRoad

data class RouteInfo(
    val cost: Int?,
    val duration: Int,
    val distance: Int,
    val isSelected: Boolean = false,
    val points: List<PointsWithColor>,
    val tollRoads: List<TollRoad>,
)
