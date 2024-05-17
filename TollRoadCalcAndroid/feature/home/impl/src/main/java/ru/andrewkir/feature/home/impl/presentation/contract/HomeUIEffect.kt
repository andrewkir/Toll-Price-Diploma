package ru.andrewkir.feature.home.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIEffect
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.Polyline

sealed class HomeUIEffect: UIEffect {

    object MoveCameraToUser: HomeUIEffect()

    class PlaceObjects(val markers: List<Marker>, val lines: List<Polyline>): HomeUIEffect()
    class MoveCameraToPoint(val point: GeoPoint): HomeUIEffect()
    class ShowSnackbar(val message: String): HomeUIEffect()
}