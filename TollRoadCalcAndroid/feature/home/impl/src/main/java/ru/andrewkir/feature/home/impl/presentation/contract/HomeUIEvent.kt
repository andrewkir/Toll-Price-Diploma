package ru.andrewkir.feature.home.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIEvent
import ru.andrewkir.feature.home.impl.domain.models.TransportClass
import ru.andrewkir.feature.home.impl.presentation.model.RouteInfo


sealed class HomeUIEvent : UIEvent {
    class OnSearchQueryPointAChanged(val query: String, val toggleSearch: Boolean = true) : HomeUIEvent()
    class OnSearchQueryPointBChanged(val query: String, val toggleSearch: Boolean = true) : HomeUIEvent()

    class PointASelected(val lat: Double, val lon: Double) : HomeUIEvent()
    class PointBSelected(val lat: Double, val lon: Double) : HomeUIEvent()

    class StartTimeSelected(val hours: Int, val minutes: Int) : HomeUIEvent()
    class TransportClassSelected(val transportClass: TransportClass) : HomeUIEvent()
    class PaymentMethodSelected(val method: Pair<String, String>?) : HomeUIEvent()

    data object MoveToMyLocation : HomeUIEvent()
    data object ClearSelectedTime : HomeUIEvent()

    data object OnApplySettingsClicked : HomeUIEvent()

    class OnRouteButtonClicked(val routeInfo: RouteInfo): HomeUIEvent()
}