package ru.andrewkir.feature.home.impl.presentation.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.andrewkir.feature.home.impl.presentation.components.content.HomeScreenContent
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIEffect
import ru.andrewkir.feature.home.impl.presentation.viewmodel.HomeScreenViewModel
import ru.dgis.sdk.Context
import ru.dgis.sdk.Duration
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.CameraAnimationType
import ru.dgis.sdk.map.CameraBehaviour
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.FollowBearing
import ru.dgis.sdk.map.FollowPosition
import ru.dgis.sdk.map.FollowStyleZoom
import ru.dgis.sdk.map.Map
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MyLocationController
import ru.dgis.sdk.map.MyLocationMapObjectSource
import ru.dgis.sdk.map.Zoom


@Composable
fun HomeScreen(
    dGisContext: Context,
    viewModel: HomeScreenViewModel,
) {
    val state by viewModel.uiState.collectAsState()

    val map = remember { mutableStateOf<Map?>(null) }
    val mapLinesObjectManager = remember { mutableStateOf<MapObjectManager?>(null) }
    val mapMarkersObjectManager = remember { mutableStateOf<MapObjectManager?>(null) }
    val mapMyLocationObjectManager = remember { mutableStateOf<MapObjectManager?>(null) }

    val isMyLocationShowing = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect: HomeUIEffect ->
            when (effect) {
                HomeUIEffect.MoveCameraToUser -> {
                    map.value?.camera?.setBehaviour(
                        CameraBehaviour(
                            FollowPosition(
                                bearing = FollowBearing.ON,
                                styleZoom = FollowStyleZoom.ON,
                            ),
                        ),
                    )

                    if (isMyLocationShowing.value == false) {
                        map.value?.addSource(
                            MyLocationMapObjectSource(
                                dGisContext,
                                MyLocationController(null),
                            )
                        )

                        isMyLocationShowing.value = true
                    }
                }

                is HomeUIEffect.PlaceObjects -> {
                    scope.launch(Dispatchers.IO) {
                        mapLinesObjectManager.value?.removeAll()
                        mapMarkersObjectManager.value?.removeAll()

                        mapLinesObjectManager.value?.addObjects(effect.lines)
                        mapMarkersObjectManager.value?.addObjects(effect.markers)
                    }
                }

                is HomeUIEffect.MoveCameraToPoint -> {
                    map.value?.camera?.setBehaviour(
                        CameraBehaviour(
                            null
                        ),
                    )
                    map.value?.camera?.move(
                        position = CameraPosition(
                            GeoPoint(effect.point.latitude, effect.point.longitude),
                            zoom = Zoom(15f),
                        ),
                        time = Duration.ofSeconds(1L),
                        animationType = CameraAnimationType.DEFAULT,
                    )
                }

                is HomeUIEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    HomeScreenContent(
        modifier = Modifier,
        uiState = state,
        onEvent = viewModel::setEvent,
        onMapReady = { mapReady ->
            map.value = mapReady

            mapLinesObjectManager.value = MapObjectManager(mapReady)
            mapMarkersObjectManager.value = MapObjectManager(mapReady)
            mapMyLocationObjectManager.value = MapObjectManager(mapReady)
        },
        snackbarHostState = snackbarHostState,
    )
}