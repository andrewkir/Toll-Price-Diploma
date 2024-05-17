package ru.andrewkir.core.presentation.map.presentation.component

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.DirectoryObject
import ru.dgis.sdk.directory.SearchLayout
import ru.dgis.sdk.directory.SearchOptions
import ru.dgis.sdk.directory.SearchType
import ru.dgis.sdk.directory.SearchViewCallback
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Zoom
import timber.log.Timber
import java.lang.IllegalStateException
import kotlin.random.Random

@Composable
fun SearchView(
    modifier: Modifier,
    onObjectChosen: (DirectoryObject) -> Unit,
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> SearchLayout(context, SearchOptions(SearchType.Online))
            .also {
                it.addSearchViewCallback(object : SearchViewCallback {
                    override fun directoryObjectChosen(obj: DirectoryObject) {
                        onObjectChosen(obj)
                    }
                })
            }}
    )
}