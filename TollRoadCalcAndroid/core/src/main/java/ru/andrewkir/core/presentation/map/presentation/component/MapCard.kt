package ru.andrewkir.core.presentation.map.presentation.component

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.Map
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Polyline
import ru.dgis.sdk.map.PolylineOptions
import ru.dgis.sdk.map.Zoom
import ru.dgis.sdk.map.lpx
import java.lang.IllegalStateException

@Composable
fun MapCard(onMapReady: (Map) -> Unit) {
    val mapView = rememberMapViewWithLifecycle()
    val scope = rememberCoroutineScope()

    AndroidView(
        { mapView }
    ) {
        scope.launch {
            it.getMapAsync { map ->
                onMapReady.invoke(map)
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }
    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember {
        LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(owner)
                Lifecycle.Event.ON_START -> mapView.onStart(owner)
                Lifecycle.Event.ON_RESUME -> mapView.onResume(owner)
                Lifecycle.Event.ON_PAUSE -> mapView.onPause(owner)
                Lifecycle.Event.ON_STOP -> mapView.onStop(owner)
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy(owner)
                Lifecycle.Event.ON_ANY -> throw IllegalStateException()
            }
        }
    }