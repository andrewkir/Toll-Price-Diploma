package ru.andrewkir.feature.home.impl.presentation.contract

import android.location.Location
import ru.andrewkir.core.presentation.contract.UIState
import ru.andrewkir.feature.home.impl.domain.models.LocationDetails
import ru.andrewkir.feature.home.impl.domain.models.Locations
import ru.andrewkir.feature.home.impl.domain.models.TransportClass
import ru.andrewkir.feature.home.impl.presentation.model.RouteInfo
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.DirectoryObject


data class HomeUIState(
    val points: List<GeoPoint>? = null,
    val pointA: GeoPoint? = null,
    val pointB: GeoPoint? = null,
    val routeInfo: List<RouteInfo> = emptyList(),
    val pointAQuery: String = "",
    val searchedALocations: List<LocationDetails> = emptyList(),
    val pointBQuery: String = "",
    val searchedBLocations: List<LocationDetails> = emptyList(),
    val isLoading: Boolean = false,
    val startTimeHours: Int? = null,
    val startTimeMinutes: Int? = null,
    val selectedTransportClass: TransportClass = TransportClass.First,
    val paymentMethods: Map<String, String> = emptyMap(),
    val selectedPaymentMethod: Pair<String, String> = "CASH" to "Наличными или картой",
): UIState {
}