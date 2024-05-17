package ru.andrewkir.feature.home.impl.presentation.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import ru.andrewkir.core.presentation.map.presentation.component.MapCard
import ru.andrewkir.core.utils.conditional
import ru.andrewkir.feature.home.impl.R
import ru.andrewkir.feature.home.impl.domain.models.TransportClass
import ru.andrewkir.feature.home.impl.presentation.components.AddressRow
import ru.andrewkir.feature.home.impl.presentation.components.BottomRoutesSelector
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIEvent
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIState
import ru.dgis.sdk.map.Map
import timber.log.Timber
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeUIState,
    onEvent: (HomeUIEvent) -> Unit,
    onMapReady: (Map) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    var barAActive by remember {
        mutableStateOf(false)
    }

    var barBActive by remember {
        mutableStateOf(false)
    }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val clockState = rememberSheetState()
    val transportClassState = rememberSheetState()
    val paymentMethodState = rememberSheetState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column {
                MapCard(
                    onMapReady = { map ->
                        onMapReady.invoke(map)
                        onEvent(HomeUIEvent.MoveToMyLocation)
                    }
                )
            }



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .conditional(!barAActive && !barBActive) {
                        clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    }
                    .background(Color(0x77CACACA)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!barAActive && !barBActive) {
                    Spacer(modifier = Modifier.padding(10.dp))
                }

                if (barBActive == false) {
                    SearchBar(
                        query = uiState.pointAQuery,
                        onQueryChange = { newValue ->
                            onEvent(HomeUIEvent.OnSearchQueryPointAChanged(newValue))
                        },
                        onSearch = {
                            barAActive = false
                        },
                        active = barAActive,
                        onActiveChange = {
                            barAActive = it
                        },
                        placeholder = {
                            Text("Начните вводить адрес")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        },
                        trailingIcon = {
                            if (barAActive) {
                                Icon(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            if (uiState.pointAQuery.isNotEmpty()) {
                                                onEvent(HomeUIEvent.OnSearchQueryPointAChanged(""))
                                            } else {
                                                barAActive = false
                                            }
                                        },
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    ) {
                        LazyColumn {
                            items(uiState.searchedALocations) { location ->
                                AddressRow(
                                    modifier = Modifier
                                        .clickable {
                                            onEvent(
                                                HomeUIEvent.OnSearchQueryPointAChanged(
                                                    query = location.building_name
                                                        ?: location.address_name
                                                        ?: location.name ?: "",
                                                    toggleSearch = false
                                                )
                                            )
                                            val point = location.point
                                            if (point != null) {
                                                onEvent(
                                                    HomeUIEvent.PointASelected(
                                                        point.lat,
                                                        point.lon
                                                    )
                                                )
                                            }
                                            barAActive = false
                                        }
                                        .padding(16.dp),
                                    address = location.building_name ?: location.address_name
                                    ?: location.name ?: "",
                                    addressOrCoordinates = location.full_name ?: "",
                                )
                            }
                        }
                    }
                }

                if (barAActive == false) {

                    SearchBar(
                        query = uiState.pointBQuery,
                        onQueryChange = { newValue ->
                            onEvent(HomeUIEvent.OnSearchQueryPointBChanged(newValue))
                        },
                        onSearch = {
                            barBActive = false
                        },
                        active = barBActive,
                        onActiveChange = {
                            barBActive = it
                        },
                        placeholder = {
                            Text("Начните вводить адрес")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        },
                        trailingIcon = {
                            if (barBActive) {
                                Icon(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            if (uiState.pointBQuery.isNotEmpty()) {
                                                onEvent(HomeUIEvent.OnSearchQueryPointBChanged(""))
                                            } else {
                                                barBActive = false
                                            }
                                        },
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    ) {
                        LazyColumn {
                            items(uiState.searchedBLocations) { location ->
                                AddressRow(
                                    modifier = Modifier
                                        .clickable {
                                            onEvent(
                                                HomeUIEvent.OnSearchQueryPointBChanged(
                                                    query = location.building_name
                                                        ?: location.address_name
                                                        ?: location.name ?: "",
                                                    toggleSearch = false
                                                )
                                            )
                                            val point = location.point
                                            if (point != null) {
                                                onEvent(
                                                    HomeUIEvent.PointBSelected(
                                                        point.lat,
                                                        point.lon
                                                    )
                                                )
                                            }
                                            barBActive = false
                                        }
                                        .padding(16.dp),
                                    address = location.building_name ?: location.address_name
                                    ?: location.name ?: "",
                                    addressOrCoordinates = location.full_name ?: "",
                                )
                            }
                        }
                    }
                }

                if (!barAActive && !barBActive) {
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

            if (!barAActive && !barBActive) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomCenter),
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(48.dp)
                            .align(Alignment.End),
                        shape = CircleShape,
                    ) {
                        IconButton(
                            onClick = { openBottomSheet = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp)
                            .align(Alignment.End),
                        shape = CircleShape,
                    ) {
                        IconButton(
                            onClick = { onEvent(HomeUIEvent.MoveToMyLocation) },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_circle),
                                contentDescription = "Circle",
                            )
                        }
                    }

                    if (uiState.routeInfo.isNotEmpty()) {
                        BottomRoutesSelector(
                            routes = uiState.routeInfo,
                            onEvent = onEvent,
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 32.dp)
                )
            }

            if (openBottomSheet) {
                ModalBottomSheet(onDismissRequest = { openBottomSheet = false }) {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 16.dp),
                        onClick = { clockState.show() }
                    ) {
                        if (uiState.startTimeHours != null && uiState.startTimeMinutes != null) {
                            Text(
                                text = "Начало поездки - ${uiState.startTimeHours}:${
                                    String.format("%02d", uiState.startTimeMinutes)
                                }"
                            )
                            Icon(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        onEvent(HomeUIEvent.ClearSelectedTime)
                                    },
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        } else {
                            Text(text = "Установить время начала поездки")
                        }
                    }

                    ClockDialog(
                        state = clockState,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            onEvent(HomeUIEvent.StartTimeSelected(hours, minutes))
                            Timber.i(hours.toString())
                        },
                        config = ClockConfig(
                            defaultTime = LocalDateTime.now().toLocalTime(),
                            is24HourFormat = true
                        ),
                    )

                    val options = TransportClass.entries.map {
                        ListOption(
                            titleText = it.title
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        onClick = { transportClassState.show() }) {

                        Text(text = uiState.selectedTransportClass.title)
                    }

                    ListDialog(
                        state = transportClassState,
                        selection = ListSelection.Single(
                            options = options,
                            positiveButton = SelectionButton(text = "Выбрать"),
                            negativeButton = SelectionButton(text = "Отмена")
                        ) { index, _ ->
                            onEvent(HomeUIEvent.TransportClassSelected(TransportClass.entries[index]))
                        }
                    )

                    val paymentOptions = uiState.paymentMethods.entries.map {
                        ListOption(
                            titleText = it.value
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        onClick = { paymentMethodState.show() }) {

                        Text(
                            text = uiState.selectedPaymentMethod.second
                        )
                    }

                    ListDialog(
                        state = paymentMethodState,
                        selection = ListSelection.Single(
                            options = paymentOptions,
                            positiveButton = SelectionButton(text = "Выбрать"),
                            negativeButton = SelectionButton(text = "Отмена")
                        ) { index, option ->
                            onEvent(
                                HomeUIEvent.PaymentMethodSelected(uiState.paymentMethods.entries
                                    .find { mapItem ->
                                        mapItem.value == option.titleText
                                    }
                                    ?.toPair()
                                )
                            )
                        }
                    )

                    ElevatedButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp)
                            .fillMaxWidth(),
                        onClick = {
                            onEvent(HomeUIEvent.OnApplySettingsClicked)
                            openBottomSheet = false
                        }) {
                        Text(
                            text = "Применить настройки"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUIState(),
        onEvent = {},
        onMapReady = {},
        snackbarHostState = SnackbarHostState()
    )
}