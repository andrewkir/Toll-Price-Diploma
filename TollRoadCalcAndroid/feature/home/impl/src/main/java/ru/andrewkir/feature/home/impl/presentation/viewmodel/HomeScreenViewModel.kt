package ru.andrewkir.feature.home.impl.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.andrewkir.core.domain.models.NetworkResult
import ru.andrewkir.core.presentation.viewmodel.BaseViewModel
import ru.andrewkir.feature.home.impl.R
import ru.andrewkir.feature.home.impl.domain.interactor.MapInteractor
import ru.andrewkir.feature.home.impl.domain.interactor.SearchInteractor
import ru.andrewkir.feature.home.impl.domain.models.Point
import ru.andrewkir.feature.home.impl.domain.models.PointsWithColor
import ru.andrewkir.feature.home.impl.domain.models.TollRoad
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIEffect
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIEvent
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIState
import ru.andrewkir.feature.home.impl.presentation.model.RouteInfo
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.Color
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.Polyline
import ru.dgis.sdk.map.PolylineOptions
import ru.dgis.sdk.map.imageFromResource
import ru.dgis.sdk.map.lpx
import java.util.Calendar
import javax.inject.Inject

@OptIn(FlowPreview::class)
@SuppressLint("MissingPermission")
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val mapInteractor: MapInteractor,
    private val searchInteractor: SearchInteractor,
    private val dGisContext: ru.dgis.sdk.Context,
) :
    BaseViewModel<HomeUIEvent, HomeUIState, HomeUIEffect>(
        HomeUIState()
    ) {

    private val searchPointAFlow: MutableSharedFlow<String> = MutableSharedFlow()
    private val searchPointBFlow: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        observeSearchPointAFlow()
        observeSearchPointBFlow()
        getPaymentMethods()
    }

    @SuppressLint("MissingPermission")
    override fun handleEvent(event: HomeUIEvent) {
        when (event) {

            HomeUIEvent.MoveToMyLocation ->
                setEffect(HomeUIEffect.MoveCameraToUser)

            HomeUIEvent.ClearSelectedTime ->
                setState { copy(startTimeHours = null, startTimeMinutes = null) }

            HomeUIEvent.OnApplySettingsClicked ->
                if (!currentState.isLoading) searchRoute()

            HomeUIEvent.OnLoadMoreRoutesClicked -> {
                if (!currentState.isLoading) searchRoute(isLoadMoreActive = true)
            }

            is HomeUIEvent.OnSearchQueryPointAChanged -> {
                setState { copy(pointAQuery = event.query) }
                if (event.toggleSearch) {
                    viewModelScope.launch {
                        searchPointAFlow.emit(event.query)
                    }
                }
            }

            is HomeUIEvent.OnSearchQueryPointBChanged -> {
                setState { copy(pointBQuery = event.query) }
                if (event.toggleSearch) {
                    viewModelScope.launch {
                        searchPointBFlow.emit(event.query)
                    }
                }
            }

            is HomeUIEvent.PointASelected -> {
                setState { copy(pointA = GeoPoint(latitude = event.lat, longitude = event.lon)) }
                if (!currentState.isLoading) searchRoute()
            }

            is HomeUIEvent.PointBSelected -> {
                setState { copy(pointB = GeoPoint(latitude = event.lat, longitude = event.lon)) }
                if (!currentState.isLoading) searchRoute()
            }

            is HomeUIEvent.StartTimeSelected -> {
                setState { copy(startTimeHours = event.hours, startTimeMinutes = event.minutes) }
            }

            is HomeUIEvent.TransportClassSelected -> {
                setState { copy(selectedTransportClass = event.transportClass) }
            }

            is HomeUIEvent.PaymentMethodSelected -> {
                val method = event.method
                if (method != null) setState { copy(selectedPaymentMethod = method) }
            }

            is HomeUIEvent.OnRouteButtonClicked -> {
                handleOnRouteButtonClicked(event.routeInfo)
            }
        }
    }

    private fun searchPointA(query: String) {
        if (query.length >= 3) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                val result = executeRequest {
                    searchInteractor.searchLocation(query)
                }

                when (result) {
                    NetworkResult.NetworkError ->
                        setEffect(HomeUIEffect.ShowSnackbar("Отсутствует подключение к интернету"))

                    is NetworkResult.GenericError ->
                        setEffect(HomeUIEffect.ShowSnackbar(result.error?.detail ?: "Ошибка"))

                    is NetworkResult.Success ->
                        setState {
                            copy(
                                searchedALocations = result.value.result?.items ?: emptyList(),
                                isLoading = false
                            )
                        }
                }
                setState {
                    copy(
                        isLoading = false
                    )
                }
            }
        } else {
            setState {
                copy(
                    searchedALocations = emptyList(),
                    pointA = null,
                )
            }
        }
    }

    private fun searchPointB(query: String) {
        if (query.length >= 3) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                val result = executeRequest {
                    searchInteractor.searchLocation(query)
                }

                when (result) {
                    NetworkResult.NetworkError ->
                        setEffect(HomeUIEffect.ShowSnackbar("Отсутствует подключение к интернету"))

                    is NetworkResult.GenericError ->
                        setEffect(HomeUIEffect.ShowSnackbar(result.error?.detail ?: "Ошибка"))

                    is NetworkResult.Success ->
                        setState {
                            copy(
                                searchedBLocations = result.value.result?.items ?: emptyList(),
                            )
                        }
                }
                setState {
                    copy(
                        isLoading = false
                    )
                }
            }
        } else {
            setState {
                copy(
                    searchedBLocations = emptyList(),
                    pointB = null,
                )
            }
        }
    }

    private fun searchRoute(isLoadMoreActive: Boolean = false) =
        viewModelScope.launch {
            val pointB = currentState.pointB
            val pointA = currentState.pointA
            if (pointB != null && pointA != null) {

                if (!isLoadMoreActive) {
                    setState { copy(routeInfo = emptyList(), isLoading = true) }
                } else {
                    setState { copy(isLoading = true) }
                }

                var calendar: Calendar? = null
                if (!isLoadMoreActive) {
                    val hours = currentState.startTimeHours
                    val minutes = currentState.startTimeMinutes

                    if (hours != null && minutes != null) {
                        calendar = Calendar.getInstance()
                        calendar.set(Calendar.HOUR, hours)
                        calendar.set(Calendar.MINUTE, minutes)
                    }
                }
                val result = executeRequest {
                    if (isLoadMoreActive) {
                        mapInteractor.getMoreRoutes(
                            Point(
                                lat = pointA.latitude.value,
                                lon = pointA.longitude.value,
                            ),
                            Point(
                                lat = pointB.latitude.value,
                                lon = pointB.longitude.value,
                            ),
                            startTime = if (calendar == null) null else calendar.timeInMillis / 1000,
                            transportClass = currentState.selectedTransportClass.number,
                            paymentMethod = currentState.selectedPaymentMethod.first,
                            sessionCookie = currentState.sessionId,
                        )
                    } else {
                        mapInteractor.getRoute(
                            Point(
                                lat = pointA.latitude.value,
                                lon = pointA.longitude.value,
                            ),
                            Point(
                                lat = pointB.latitude.value,
                                lon = pointB.longitude.value,
                            ),
                            startTime = if (calendar == null) null else calendar.timeInMillis / 1000,
                            transportClass = currentState.selectedTransportClass.number,
                            paymentMethod = currentState.selectedPaymentMethod.first,
                        )
                    }
                }
                when (result) {
                    NetworkResult.NetworkError ->
                        setEffect(HomeUIEffect.ShowSnackbar("Отсутствует подключение к интернету"))

                    is NetworkResult.GenericError ->
                        setEffect(HomeUIEffect.ShowSnackbar(result.error?.detail ?: "Ошибка"))

                    is NetworkResult.Success -> {
                        if (!isLoadMoreActive) {
                            val cookie = result.value.headers()["Set-Cookie"]
                            if (cookie != null) {
                                val params = cookie.split(';')
                                val sessionId =
                                    params.find { parameter -> parameter.startsWith("sessionid") }

                                if (sessionId != null) {
                                    setState { copy(sessionId = "$sessionId;") }
                                } else {
                                    setState { copy(sessionId = null) }
                                }
                            } else {
                                setState { copy(sessionId = null) }
                            }
                            setState { copy(routeInfo = emptyList()) }
                        }

                        result.value.body()?.forEachIndexed { index, route ->
                            val routeInfos = currentState.routeInfo.toMutableList()
                            val routeInfo = RouteInfo(
                                cost = route.total_cost,
                                duration = route.total_duration,
                                distance = route.total_distance,
                                isSelected = index == 0 && !isLoadMoreActive,
                                points = route.points,
                                tollRoads = route.toll_roads
                            )
                            routeInfos.add(routeInfo)
                            setState { copy(routeInfo = routeInfos) }
                            if (index == 0 && !isLoadMoreActive) {
                                drawRouteOnMap(routeInfo)
                            }
                        }

                        if (result.value.body()?.isEmpty() != false) {
                            setState { copy(sessionId = null) }
                        }
                    }
                }
                setState {
                    copy(
                        isLoading = false
                    )
                }
            }
        }

    private fun observeSearchPointAFlow() {
        viewModelScope.launch {
            searchPointAFlow
                .debounce(1000L)
                .collectLatest { query ->
                    searchPointA(query)
                }
        }
    }

    private fun observeSearchPointBFlow() {
        viewModelScope.launch {
            searchPointBFlow
                .debounce(1000L)
                .collectLatest { query ->
                    searchPointB(query)
                }
        }
    }

    private fun getPaymentMethods() =
        viewModelScope.launch {
            val result = executeRequest {
                mapInteractor.getPaymentMethods()
            }

            when (result) {
                NetworkResult.NetworkError ->
                    setEffect(HomeUIEffect.ShowSnackbar("Отсутствует подключение к интернету"))

                is NetworkResult.GenericError ->
                    setEffect(HomeUIEffect.ShowSnackbar(result.error?.detail ?: "Ошибка"))

                is NetworkResult.Success ->
                    setState {
                        copy(
                            paymentMethods = result.value,
                            selectedPaymentMethod = result.value.entries.first().toPair()
                        )
                    }
            }
        }

    private fun handleOnRouteButtonClicked(routeButton: RouteInfo) {
        val routes = currentState.routeInfo.toMutableList()

        val currentRouteIndex = routes.indexOfFirst { it.isSelected }
        if (currentRouteIndex == -1) return

        val currentRoute = routes[currentRouteIndex]
        val oldUnselectedRoute = currentRoute.copy(isSelected = false)

        val selectedRouteIndex = routes.indexOfFirst { it == routeButton }
        if (selectedRouteIndex == -1) return

        val selectedRoute = routes[selectedRouteIndex]
        val newSelectedRoute = selectedRoute.copy(isSelected = true)

        routes[currentRouteIndex] = oldUnselectedRoute
        routes[selectedRouteIndex] = newSelectedRoute

        setState {
            copy(routeInfo = routes)
        }
        drawRouteOnMap(newSelectedRoute, false)
    }

    private fun getLineFromPoints(points: List<Point>): MutableList<GeoPoint> {
        val line = mutableListOf<GeoPoint>()
        points.forEach { point ->
            line.add(GeoPoint(latitude = point.lat, longitude = point.lon))
        }
        return line
    }

    private fun getLineFromTcp(tollRoads: List<TollRoad>): MutableList<GeoPoint> {
        val tcp = mutableListOf<GeoPoint>()
        tollRoads.forEach { tollRoad ->
            tollRoad.tcp_list.forEach { point ->
                tcp.add(
                    GeoPoint(
                        latitude = point.lat,
                        longitude = point.lon
                    )
                )
            }
        }
        return tcp
    }

    private fun drawRouteOnMap(route: RouteInfo, moveToStart: Boolean = true) {
        val line = route.points
        val tcp = getLineFromTcp(route.tollRoads)
        val pointA = currentState.pointA
        val pointB = currentState.pointB

        if (pointA != null && pointB != null) {
            placeRouteAndTcpOnMap(
                line,
                tcp,
                pointA,
                pointB,
                moveToStart = moveToStart
            )
        }
    }

    private fun placeRouteAndTcpOnMap(
        pointsWithColors: List<PointsWithColor>,
        tcp: List<GeoPoint>,
        pointA: GeoPoint,
        pointB: GeoPoint,
        moveToStart: Boolean,
    ) {
        val markers = mutableListOf<Marker>()
        val lines = mutableListOf<Polyline>()

        pointsWithColors.forEach { pointsWithColor ->
            lines.add(
                Polyline(
                    PolylineOptions(
                        points = getLineFromPoints(pointsWithColor.selection),
                        width = 4.lpx,
                        color = getColorFromResponse(pointsWithColor.color)
                    )
                )
            )
        }

        tcp.forEach {
            markers.add(
                Marker(
                    MarkerOptions(
                        position = GeoPointWithElevation(
                            latitude = it.latitude,
                            longitude = it.longitude,
                        ),
                        icon = imageFromResource(
                            dGisContext,
                            R.drawable.ic_money
                        )
                    )
                )
            )
        }

        markers.add(
            Marker(
                MarkerOptions(
                    position = GeoPointWithElevation(
                        latitude = pointA.latitude,
                        longitude = pointA.longitude
                    ),
                    icon = imageFromResource(dGisContext, R.drawable.ic_place)
                )
            )
        )
        markers.add(
            Marker(
                MarkerOptions(
                    position = GeoPointWithElevation(
                        latitude = pointB.latitude,
                        longitude = pointB.longitude
                    ),
                    icon = imageFromResource(dGisContext, R.drawable.ic_place)
                )
            )
        )

        setEffect(HomeUIEffect.PlaceObjects(markers, lines))
        if (moveToStart) {
            setEffect(HomeUIEffect.MoveCameraToPoint(pointA))
        }
    }

    private fun getColorFromResponse(color: String): Color {
        return when (color) {
            "fast" -> Color(0, 230, 0)
            "normal" -> Color(255, 165, 0)
            "slow" -> Color(255, 0, 0)
            "ignore" -> Color(128, 128, 128)
            "no-traffic" -> Color(139, 0, 255)
            else -> Color(128, 128, 128)
        }
    }
}